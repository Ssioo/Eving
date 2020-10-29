package com.whoissio.eving.views

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.Entry
import com.orhanobut.logger.Logger
import com.polidea.rxandroidble2.RxBleClient
import com.whoissio.eving.BaseFragment
import com.whoissio.eving.R
import com.whoissio.eving.databinding.FragmentExerciseMeasureBinding
import com.whoissio.eving.models.Sensor
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.utils.Helpers.initChart
import com.whoissio.eving.utils.SensorType
import com.whoissio.eving.utils.objects.SingleEvent
import com.whoissio.eving.viewmodels.ExerciseMeasureViewModel
import com.whoissio.eving.viewmodels.ExerciseViewModel
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.toImmutableList
import java.nio.ByteBuffer
import kotlin.math.max
import kotlin.math.sqrt

class ExerciseMeasureFragment
    : BaseFragment<FragmentExerciseMeasureBinding, ExerciseMeasureViewModel, ExerciseViewModel>(R.layout.fragment_exercise_measure) {

    private val disposable = CompositeDisposable()

    override fun getViewModel(): ExerciseMeasureViewModel = ViewModelProvider(this).get(ExerciseMeasureViewModel::class.java)

    override fun getParentViewModel(): ExerciseViewModel = ViewModelProvider(requireActivity()).get(ExerciseViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

        binding.chartExercise.apply { initChart(vm.chartData) }

        vm.refreshChart.observe(this) {
            it.get()?.let { if (it) { binding.chartExercise.apply { initChart(vm.chartData) } }}
        }

        vm.visibleSensorType.observe(this) {
            binding.chartExercise.apply {
                data?.dataSets?.apply {
                    forEachIndexed { idx, _ -> get(idx).isVisible = it.chartIdx == idx }
                }
                axisRight.axisMinimum = if (it.type == SensorType.TILT.type) 0f else -360f
                invalidate()
            }
        }

        vm.newAccSensorDatum.observe(this) {
            vm.chartData.dataSets?.apply {
                listOf(
                    SensorType.ACC_X.chartIdx,
                    SensorType.ACC_Y.chartIdx,
                    SensorType.ACC_Z.chartIdx
                )
                    .forEachIndexed { idx, i -> get(i)?.apply { addEntry(Entry(entryCount.toFloat(), it.data[idx])) } }
            }
            vm.speed.value = max(vm.speed.value!! , sqrt(it.data.fold(0f, { total, cur -> total + cur * cur}).toDouble()) * 35.30394)
            binding.chartExercise.apply {
                moveViewToX(data.entryCount.toFloat())
                if (vm.visibleSensorType.value?.type == SensorType.ACC_X.type)
                    notifyDataSetChanged()
            }
        }

        vm.newGyroSensorDatum.observe(this) {
            vm.chartData.dataSets?.apply {
                listOf(
                    SensorType.GYRO_X.chartIdx,
                    SensorType.GYRO_Y.chartIdx,
                    SensorType.GYRO_Z.chartIdx
                )
                    .forEachIndexed { idx, i -> get(i)?.apply { addEntry(Entry(entryCount.toFloat(), it.data[idx])) } }
            }
            binding.chartExercise.apply {
                moveViewToX(data.entryCount.toFloat())
                if (vm.visibleSensorType.value?.type == SensorType.GYRO_X.type)
                    notifyDataSetChanged()
            }
        }

        vm.newTiltSensorDatum.observe(this) {
            vm.chartData.dataSets?.apply {
                get(SensorType.TILT.chartIdx)?.apply {
                    addEntry(Entry(entryCount.toFloat(), it.data[0]))
                }
            }
            binding.chartExercise.apply {
                moveViewToX(data.entryCount.toFloat())
                if (vm.visibleSensorType.value?.type == SensorType.TILT.type)
                    notifyDataSetChanged()
            }
        }

        vm.isRecording.observe(this) {
            if (it) {
                registerNotificationOnBleDevice()
            } else {
                if (vm.curAccSensorData.isEmpty()) return@observe
                pvm.fullAccSensorData.add(vm.curAccSensorData.toList())
                pvm.fullGyroSensorData.add(vm.curGyroSensorData.toList())
                pvm.fullTiltSensorData.add(vm.curTiltSensorData.toList())
                pvm.fullMagSensorData.add(vm.curMagSensorData.toList())
                if (findNavController().currentDestination?.id == R.id.fg_measure)
                    findNavController().navigate(R.id.action_to_result)
            }
        }

        vm.myLastBle.observe(this) {
            if (it != null) binding.btnStart.isEnabled = true
        }

        binding.btnNextSet.setOnClickListener {
            if (!lock) createNewExerciseSet()
        }
    }

    fun registerNotificationOnBleDevice() {
        if (disposable.size() == 0 && vm.myLastBle.value != null)
            disposable.add(
                RxBleClient.create(requireActivity())
                    .getBleDevice(vm.myLastBle.value!!)
                    .establishConnection(false)
                    .concatMap { it.setupNotification(Constants.UUID_DEVICE2) }
                    .concatMap { it }
                    .subscribe({
                        if (vm.isRecording.value != true) return@subscribe
                        val timeCount = ByteBuffer.wrap(listOf(it[19], it[18], it[17], it[16]).toByteArray()).int
                        if (it[0] == 2.toByte()) {
                            // tact button clicked
                            if (!lock) createNewExerciseSet()
                        } else {
                            when (it[3]) {
                                60.toByte() -> {
                                    val x = ByteBuffer.wrap(listOf(it[7], it[6], it[5], it[4]).toByteArray()).float
                                    val y = ByteBuffer.wrap(listOf(it[11], it[10], it[9], it[8]).toByteArray()).float
                                    val z = ByteBuffer.wrap(listOf(it[15], it[14], it[13], it[12]).toByteArray()).float
                                    Logger.d("acc at $timeCount: ${listOf(x, y, z)}")
                                    vm.newAccSensorDatum.postValue(Sensor(listOf(x, y, z)))
                                    vm.curAccSensorData.add(Sensor(listOf(x, y, z)))
                                }
                                61.toByte() -> {
                                    val x = ByteBuffer.wrap(listOf(it[7], it[6], it[5], it[4]).toByteArray()).float
                                    val y = ByteBuffer.wrap(listOf(it[11], it[10], it[9], it[8]).toByteArray()).float
                                    val z = ByteBuffer.wrap(listOf(it[15], it[14], it[13], it[12]).toByteArray()).float
                                    Logger.d("gyro at $timeCount: ${listOf(x, y, z)}")
                                    vm.newGyroSensorDatum.postValue(Sensor(listOf(x, y, z)))
                                    vm.curGyroSensorData.add(Sensor(listOf(x, y, z)))
                                }
                                62.toByte() -> {
                                    var tilt = ByteBuffer.wrap(listOf(it[7], it[6], it[5], it[4]).toByteArray()).float
                                    tilt = if (tilt.isNaN()) 90f else tilt
                                    Logger.d("tilt at $timeCount: $tilt")
                                    vm.newTiltSensorDatum.postValue(Sensor(listOf(tilt)))
                                    vm.curTiltSensorData.add(Sensor(listOf(tilt)))
                                }
                                63.toByte() -> {
                                    val x = ByteBuffer.wrap(listOf(it[7], it[6], it[5], it[4]).toByteArray()).float
                                    val y = ByteBuffer.wrap(listOf(it[11], it[10], it[9], it[8]).toByteArray()).float
                                    val z = ByteBuffer.wrap(listOf(it[15], it[14], it[13], it[12]).toByteArray()).float
                                    Logger.d("Mag at $timeCount: ${listOf(x, y, z)}")
                                    vm.curMagSensorData.add(Sensor(listOf(x, y, z)))
                                }
                            }
                        }
                    }, {
                        it.printStackTrace()
                    })
            )
    }

    var lock = false

    // Background Thread
    fun createNewExerciseSet() {
        lock = true
        vm.toastEvent.postValue(SingleEvent(data = R.string.exercise_new_set))
        pvm.fullAccSensorData.add(vm.curAccSensorData.toImmutableList())
        pvm.fullGyroSensorData.add(vm.curGyroSensorData.toImmutableList())
        pvm.fullTiltSensorData.add(vm.curTiltSensorData.toImmutableList())
        pvm.fullMagSensorData.add(vm.curMagSensorData.toImmutableList())
        vm.curAccSensorData.clear()
        vm.curGyroSensorData.clear()
        vm.curTiltSensorData.clear()
        vm.curMagSensorData.clear()
        vm.speed.postValue(0.0)
        pvm.fullChartData.add(vm.chartData)
        pvm.totalSet.postValue(pvm.fullAccSensorData.size + 1)
        vm.setNewChart()
        Thread {
            Thread.sleep(200)
            lock = false
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }
}