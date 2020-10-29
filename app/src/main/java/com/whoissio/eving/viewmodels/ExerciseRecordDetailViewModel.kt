package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.github.mikephil.charting.data.LineData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.adapters.SetButtonListAdapter
import com.whoissio.eving.models.SensorData
import com.whoissio.eving.networks.services.ExerciseService
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.SensorType
import kotlin.math.sqrt

class ExerciseRecordDetailViewModel(
    val exerciseId: Int,
    val title: String?,
    val createdAt: String?
) : BaseViewModel() {

    val visibleSensorType: MutableLiveData<SensorType> = MutableLiveData(SensorType.ACC_X)
    val sensorSetGroupedData: MutableLiveData<List<List<SensorData?>>> = MutableLiveData(listOf())
    val sensorTimeDistancesByAccX = Transformations.map(sensorSetGroupedData) {
        it.map { data -> data.indexOfFirst { it?.accX == data.minOf { it?.accX!! } } }.let { res ->
            res.map { res.maxOf { it } - it }
        }
    }
    val speeds = Transformations.map(sensorSetGroupedData) {
        it.map {
            it.maxOf {
                sqrt(it?.accX!! * it.accX + it.accY * it.accY + it.accZ * it.accZ).toDouble() * 35.30904
            }
        }
    }
    val avgSpeed = Transformations.map(speeds) { it.average() }
    val exerciseAmount = MutableLiveData(0.0)
    val horizontalBias = MutableLiveData(0.0)
    val sets = Transformations.switchMap(sensorSetGroupedData) {
        MutableLiveData(it.mapIndexed { idx, _ ->
            SetButtonListAdapter.SelectableIdx(idx, idx == 0)
        })
    }
    val selectedSet = MutableLiveData(0)
    val setButtonListAdapter = SetButtonListAdapter(this)

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
            it?.let {
                it.data?.let { data ->
                    sensorSetGroupedData.value = data.map { it?.setIdx }.toSet().map { set ->
                        data.filter { it?.setIdx == set }
                    }
                }
            }
        }, {
            doOnNetworkError(it)
        })
    }

    init {
        fetchSensorData(exerciseId)
    }
}