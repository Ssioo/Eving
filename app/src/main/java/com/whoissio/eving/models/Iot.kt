package com.whoissio.eving.models

import com.google.gson.annotations.SerializedName

enum class IotType(val type: String) {
    CUSTOM("custom"),
    WATCH("watch"),
    BAND("band")
}

data class IotDevice(
    @SerializedName("id") val id: Int,
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("address") val address: String,
    @SerializedName("type") val type: IotType,
    @SerializedName("created_at") val createdAt: String
)

data class IotRegisterParam(
    @SerializedName("uuid") val uuid: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("address") val address: String,
    @SerializedName("type") val type: IotType,
)