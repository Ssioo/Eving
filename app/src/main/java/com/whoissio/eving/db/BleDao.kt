package com.whoissio.eving.db

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.whoissio.eving.models.BleDevice

interface BleDao {

    @Query("SELECT * from BLE_DEVICE_TB")
    fun getAllMyDevices() : LiveData<List<BleDevice>>

    @Query("SELECT * from BLE_DEVICE_TB WHERE isDeleted = 0")
    fun getAllMyActiveDevices() : LiveData<List<BleDevice>>
}