package com.whoissio.eving.utils

import android.app.Service
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.orhanobut.logger.Logger


class BleGattService : Service() {

    private var mBluetoothManager: BluetoothManager? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothGatt: BluetoothGatt? = null
    private var connectedAddress: String? = null
    private var connectionState = Constants.STATE_DISCONNECTED

    // Various callback methods defined by the BLE API.
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            val intentAction: String
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    intentAction = Constants.ACTION_GATT_CONNECTED
                    connectionState = Constants.STATE_CONNECTED
                    broadcastUpdate(intentAction)
                    Logger.d("BLESERVICE Connected to GATT server.")
                    Logger.d("Attempting to start service discovery: " + bluetoothGatt?.discoverServices())
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    intentAction = Constants.ACTION_GATT_DISCONNECTED
                    connectionState = Constants.STATE_DISCONNECTED
                    Logger.d("Disconnected from GATT server.")
                    broadcastUpdate(intentAction)
                }
            }
        }

        // New services discovered
        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            when (status) {
                BluetoothGatt.GATT_SUCCESS -> {
                    broadcastUpdate(Constants.ACTION_GATT_SERVICES_DISCOVERED)
                    gatt.services.forEach {
                        setCharacteristicNotification(
                            BluetoothGattCharacteristic(
                                Constants.UUID_DEVICE2,
                                BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                                BluetoothGattCharacteristic.PERMISSION_READ
                            ), true
                        )
                    }
                }
                else -> Logger.d("onServicesDiscovered received: $status")
            }
        }


        // Result of a characteristic read operation
        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            Logger.d(characteristic)
            when (status) {
                BluetoothGatt.GATT_SUCCESS -> {
                    broadcastUpdate(Constants.ACTION_DATA_AVAILABLE, characteristic)
                }
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            Logger.d(characteristic?.value)
        }

        override fun onDescriptorWrite(
            gatt: BluetoothGatt?,
            descriptor: BluetoothGattDescriptor?,
            status: Int
        ) {
            super.onDescriptorWrite(gatt, descriptor, status)
            Logger.d(descriptor?.value)
        }

        override fun onDescriptorRead(
            gatt: BluetoothGatt?,
            descriptor: BluetoothGattDescriptor?,
            status: Int
        ) {
            super.onDescriptorRead(gatt, descriptor, status)
            Logger.d(descriptor?.value)
        }
    }

    private fun broadcastUpdate(action: String) {
        val intent = Intent(action)
        sendBroadcast(intent)
    }

    private fun broadcastUpdate(action: String, characteristic: BluetoothGattCharacteristic) {
        val intent = Intent(action)
        Logger.d("uuid" + characteristic.uuid)
        Logger.d("value" + characteristic.value.toString())
        Logger.d("hexString" + characteristic.value.joinToString(separator = " ") {
            String.format("%02X", it)
        })
        // This is special handling for the Heart Rate Measurement profile. Data
        // parsing is carried out as per profile specifications.
        /*when (characteristic.uuid) {
            Constants.UUID_DEVICE -> {
                val flag = characteristic.properties
                val format = when (flag and 0x01) {
                    0x01 -> {
                        Logger.d("Heart rate format UINT16.")
                        BluetoothGattCharacteristic.FORMAT_UINT16
                    }
                    else -> {
                        Logger.d("Heart rate format UINT8.")
                        BluetoothGattCharacteristic.FORMAT_UINT8
                    }
                }
                val heartRate = characteristic.getIntValue(format, 1)
                Logger.d(String.format("Received heart rate: %d", heartRate))
                intent.putExtra(Constants.EXTRA_DATA, (heartRate).toString())
            }
            else -> {
                // For all other profiles, writes the data formatted in HEX.
                val data: ByteArray? = characteristic.value
                if (data?.isNotEmpty() == true) {
                    val hexString: String = data.joinToString(separator = " ") {
                        String.format("%02X", it)
                    }
                    intent.putExtra(Constants.EXTRA_DATA, "$data\n$hexString")
                }
            }
        }*/
        sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder? = mBinder

    override fun onUnbind(intent: Intent?): Boolean {
        close()
        return super.onUnbind(intent)
    }

    fun initialize(): Boolean {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            if (mBluetoothManager == null) {
                Logger.d("Unable to initialize BluetoothManager.")
                return false
            }
        }
        mBluetoothAdapter = mBluetoothManager?.adapter
        if (mBluetoothAdapter == null) {
            Logger.d("Unable to obtain a BluetoothAdapter.")
            return false
        }
        return true
    }

    fun connect(address: String?): Boolean {
        if (mBluetoothAdapter == null || address == null) {
            Logger.d("BluetoothAdapter not initialized or unspecified address.")
            return false
        }
        // Previously connected device.  Try to reconnect.
        if (connectedAddress != null && address == connectedAddress && bluetoothGatt != null) {
            Logger.d("Trying to use an existing mBluetoothGatt for connection.")
            return if (bluetoothGatt?.connect() == true) {
                connectionState = Constants.STATE_CONNECTING
                true
            } else {
                false
            }
        }
        val device: BluetoothDevice? = mBluetoothAdapter?.getRemoteDevice(address)
        if (device == null) {
            Logger.d("Device not found. Unable to connect.")
            return false
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        bluetoothGatt = device.connectGatt(this, false, gattCallback)
        Logger.d("Trying to create a new connection.")
        connectedAddress = address
        connectionState = Constants.STATE_CONNECTING
        return true
    }

    fun disconnect() {
        if (mBluetoothAdapter == null || bluetoothGatt == null) {
            Logger.d("BluetoothAdapter not initialized")
            return
        }
        bluetoothGatt?.disconnect()
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    fun close() {
        bluetoothGatt?.close()
        bluetoothGatt = null
    }

    fun readCharacteristic(characteristic: BluetoothGattCharacteristic?) {
        if (mBluetoothAdapter == null || bluetoothGatt == null) {
            Logger.d("BluetoothAdapter not initialized")
            return
        }
        bluetoothGatt?.readCharacteristic(characteristic)
    }

    fun setCharacteristicNotification(
        characteristic: BluetoothGattCharacteristic,
        enabled: Boolean
    ) {
        if (mBluetoothAdapter == null || bluetoothGatt == null) {
            Logger.d("BluetoothAdapter not initialized")
            return
        }
        bluetoothGatt?.setCharacteristicNotification(characteristic, enabled)
        // This is specific to Heart Rate Measurement.
        if (Constants.UUID_DEVICE2 == characteristic.uuid) {

        }
        characteristic.descriptors.forEach {
            bluetoothGatt?.readDescriptor(it.apply {
                value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            })
        }
    }

    fun getSupportedGattServices(): List<BluetoothGattService?>? = bluetoothGatt?.services

    private val mBinder = BleServiceBinder()

    inner class BleServiceBinder : Binder() {
        fun getService(): BleGattService = this@BleGattService
    }
}