package com.whoissio.eving.models

enum class IotType(val type: String) {
    CUSTOM("custom"),
    WATCH("watch"),
    BAND("band")
}

data class IotDevice(
    val uuid: String?,
    val name: String?,
    val address: String,
    val type: IotType,
    val createdAt: String
)