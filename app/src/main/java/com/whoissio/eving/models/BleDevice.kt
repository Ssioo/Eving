package com.whoissio.eving.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BLE_DEVICE_TB")
data class BleDevice(
    @PrimaryKey
    val uuid: String,
    val name: String?,
    val address: String,
    val createdAt: String,
    val isDeleted: Boolean = false
)