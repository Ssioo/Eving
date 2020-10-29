package com.whoissio.eving.models

import com.google.gson.annotations.SerializedName

data class ExerciseSensorDataParam(
    @SerializedName("sensors") val sensors: List<List<List<Float>>>,
    @SerializedName("avgAcc") val avgAcc: List<List<Double>>,
    @SerializedName("avgGyro") val avgGyro: List<List<Double>>,
    @SerializedName("avgTilt") val tilt: List<List<Double>>,
)

data class ExerciseId(
    @SerializedName("exerciseId") val exerciseId: Int
)

data class Exercise(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("created_at") val createdAt: String,
)