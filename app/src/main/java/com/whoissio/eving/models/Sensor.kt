package com.whoissio.eving.models

import com.google.gson.annotations.SerializedName

data class Sensor(
    //@SerializedName("time") val time: Int,
    @SerializedName("data") val data: List<Float> // [x, y, z] or [tilt]
)

data class SensorData(
    @SerializedName("acc_x") val accX: Float,
    @SerializedName("acc_y") val accY: Float,
    @SerializedName("acc_z") val accZ: Float,
    @SerializedName("gyro_x") val gyroX: Float,
    @SerializedName("gyro_y") val gyroY: Float,
    @SerializedName("gyro_z") val gyroZ: Float,
    @SerializedName("mag_x") val magX: Float,
    @SerializedName("mag_y") val magY: Float,
    @SerializedName("mag_z") val magZ: Float,
    @SerializedName("tilt") val tilt: Float,
    @SerializedName("set_idx") val setIdx: Float,
)