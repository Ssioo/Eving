package com.whoissio.eving.views

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivityExerciseRecordDetailBinding
import com.whoissio.eving.utils.Constants.COLOR_SET
import com.whoissio.eving.utils.Helpers.initChart
import com.whoissio.eving.utils.SensorType
import com.whoissio.eving.viewmodels.ExerciseRecordDetailViewModel

class ExerciseRecordDetailActivity :
    BaseActivity<ActivityExerciseRecordDetailBinding, ExerciseRecordDetailViewModel>(R.layout.activity_exercise_record_detail) {

    override fun getViewModel(): ExerciseRecordDetailViewModel = ViewModelProvider(
        this,
        ExerciseRecordDetailViewModelFactory(intent.getIntExtra("id", -1), intent.getStringExtra("title"), intent.getStringExtra("createdAt"))
    ).get(ExerciseRecordDetailViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        viewmodel.sensorSetGroupedData.observe(this) {
            if (it.isEmpty()) return@observe
            viewmodel.chartData.addAll(
                listOf(
                    SensorType.ACC_X,
                    SensorType.ACC_Y,
                    SensorType.ACC_Z,
                    SensorType.GYRO_X,
                    SensorType.GYRO_Y,
                    SensorType.GYRO_Z,
                    SensorType.TILT
                )
                    .map { type ->
                        LineData(ArrayList<ILineDataSet>().apply {
                            it.forEachIndexed { idx, data ->
                                add(LineDataSet(
                                    ArrayList<Entry>().apply { add(Entry(0f, 0f)) },
                                    "Set ${idx + 1}"
                                ).apply {
                                    colors = listOf(COLOR_SET[idx % COLOR_SET.size])
                                    circleColors = listOf(COLOR_SET[idx % COLOR_SET.size])
                                    mode = LineDataSet.Mode.CUBIC_BEZIER
                                    data.forEach {
                                        when (type) {
                                            SensorType.ACC_X -> it?.accX
                                            SensorType.ACC_Y -> it?.accY
                                            SensorType.ACC_Z -> it?.accZ
                                            SensorType.GYRO_X -> it?.gyroX
                                            SensorType.GYRO_Y -> it?.gyroY
                                            SensorType.GYRO_Z -> it?.gyroZ
                                            SensorType.TILT -> it?.tilt
                                        }?.let { addEntry(Entry(entryCount.toFloat(), it)) }
                                    }
                                })
                            }

                        })
                    }
            )
            binding?.chartExercise?.apply {
                viewmodel.chartData.getOrNull(viewmodel.visibleSensorType.value?.chartIdx ?: 0)?.let {
                    initChart(it)
                }
            }
        }

        viewmodel.visibleSensorType.observe(this) {
            binding?.chartExercise?.apply {
                viewmodel.chartData.getOrNull(it.chartIdx)?.let {
                    initChart(it)
                }
            }
        }
    }

    class ExerciseRecordDetailViewModelFactory(private val exerciseId: Int, private val title: String?, private val createdAt: String?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            ExerciseRecordDetailViewModel(exerciseId, title, createdAt) as T
    }
}