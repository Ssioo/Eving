package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.utils.SensorType
import java.lang.Exception

class ExerciseResultViewModel : BaseViewModel() {

    val visibleSensorType: MutableLiveData<SensorType> = MutableLiveData(SensorType.ACC_X)

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
}