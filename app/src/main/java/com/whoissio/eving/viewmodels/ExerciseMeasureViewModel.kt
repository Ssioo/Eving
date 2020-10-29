package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.R
import com.whoissio.eving.models.Sensor
import com.whoissio.eving.networks.services.IotService
import com.whoissio.eving.utils.Constants.ALL_SENSORTYPES
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.SensorType
import com.whoissio.eving.utils.objects.SingleEvent

class ExerciseMeasureViewModel : BaseViewModel() {

    val myLastBle: MutableLiveData<String?> = MutableLiveData(null)

    val visibleSensorType: MutableLiveData<SensorType> = MutableLiveData(SensorType.ACC_X)
    val isRecording: MutableLiveData<Boolean> = MutableLiveData(false)
    val speed: MutableLiveData<Double> = MutableLiveData(0.0)
    val exerciseAmount: MutableLiveData<Double> = MutableLiveData(0.0)
    val horizontalBias: MutableLiveData<Double> = MutableLiveData(0.0)

    var newAccSensorDatum: MutableLiveData<Sensor> = MutableLiveData()
    var newGyroSensorDatum: MutableLiveData<Sensor> = MutableLiveData()
    var newTiltSensorDatum: MutableLiveData<Sensor> = MutableLiveData()
    val curAccSensorData: ArrayList<Sensor> = ArrayList()
    val curGyroSensorData: ArrayList<Sensor> = ArrayList()
    val curTiltSensorData: ArrayList<Sensor> = ArrayList()
    val curMagSensorData: ArrayList<Sensor> = ArrayList()
    val refreshChart: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()

    var chartData = LineData(ArrayList<ILineDataSet>().apply {
        ALL_SENSORTYPES.forEach {
            add(
                LineDataSet(
                    ArrayList<Entry>().apply { add(Entry(0f, 0f)) },
                    it.label
                ).apply {
                    colors = listOf(it.color)
                    circleColors = listOf(it.color)
                    axisDependency =
                        if (it.type == 0) YAxis.AxisDependency.LEFT else YAxis.AxisDependency.RIGHT
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                })
        }
    })

    fun toggleRecording() {
        isRecording.value = !isRecording.value!!
    }

    fun toggleVisibleSensorType() {
        visibleSensorType.value = when (visibleSensorType.value) {
            SensorType.ACC_X -> SensorType.ACC_Y
            SensorType.ACC_Y -> SensorType.ACC_Z
            SensorType.ACC_Z -> SensorType.GYRO_X
            SensorType.GYRO_X -> SensorType.GYRO_Y
            SensorType.GYRO_Y -> SensorType.GYRO_Z
            SensorType.GYRO_Z -> SensorType.TILT
            SensorType.TILT -> SensorType.ACC_X
            else -> throw Exception("No such Sensor type")
        }
    }

    fun setNewChart() {
        chartData = LineData(ArrayList<ILineDataSet>().apply {
            ALL_SENSORTYPES.forEach {
                add(
                    LineDataSet(
                        ArrayList<Entry>().apply { add(Entry(0f, 0f)) },
                        it.label
                    ).apply {
                        colors = listOf(it.color)
                        circleColors = listOf(it.color)
                        axisDependency =
                            if (it.type == 0) YAxis.AxisDependency.LEFT else YAxis.AxisDependency.RIGHT
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                    })
            }
        })
        refreshChart.postValue(SingleEvent(data = true))
    }

    fun fetchIotDevice() {
        IotService().fetchMyIotDevices().map {
            if (it.code == 200) it.data
            else throw Error("Network Error")
        }.toDisposal(rxDisposable, {
            myLastBle.value = it?.lastOrNull()?.address
            if (it == null || it.isEmpty()) {
                alertEvent.value = SingleEvent(data = R.string.no_device)
            }
        }, {
            doOnNetworkError(it)
        })
    }

    init {
        fetchIotDevice()
    }
}