package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.LineData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.models.SensorData
import com.whoissio.eving.networks.services.ExerciseService
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.SensorType
import java.lang.Exception

class ExerciseRecordDetailViewModel(val exerciseId: Int, val title: String?, val createdAt: String?): BaseViewModel() {

    val visibleSensorType: MutableLiveData<SensorType> = MutableLiveData(SensorType.ACC_X)
    val sensorSetGroupedData: MutableLiveData<List<List<SensorData?>>> = MutableLiveData(listOf())

    val chartData: ArrayList<LineData> = ArrayList()

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

    fun fetchSensorData(exerciseId: Int) {
        networkEvent.startLoading()
        ExerciseService().getExerciseSensorData(exerciseId).toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let { it.data?.let { data -> sensorSetGroupedData.value = data.map { it?.setIdx }.toSet().map { set ->
                data.filter { it?.setIdx == set }
            } } }
        }, {
            doOnNetworkError(it)
        })
    }

    init {
        fetchSensorData(exerciseId)
    }
}