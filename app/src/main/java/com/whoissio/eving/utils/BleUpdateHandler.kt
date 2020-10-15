package com.whoissio.eving.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.whoissio.eving.R

class BleUpdateHandler(
    val onConnected: () -> Unit,
    val onDisconnected: () -> Unit,
    val onDiscovered: () -> Unit,
    val onDataAvailable: (String?) -> Unit
) : BroadcastReceiver() {

    private lateinit var bluetoothLeService: BleGattService

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        when (action) {
            Constants.ACTION_GATT_CONNECTED -> {
                onConnected()
                /*connected = true
                updateConnectionState(R.string.connected)
                (context as? Activity)?.invalidateOptionsMenu()*/
            }
            Constants.ACTION_GATT_DISCONNECTED -> {
                onDisconnected()
                /*connected = false
                updateConnectionState(R.string.disconnected)
                (context as? Activity)?.invalidateOptionsMenu()
                clearUI()*/
            }
            Constants.ACTION_GATT_SERVICES_DISCOVERED -> {
                onDiscovered()
                // Show all the supported services and characteristics on the
                // user interface.
                //displayGattServices(bluetoothLeService.getSupportedGattServices())
            }
            Constants.ACTION_DATA_AVAILABLE -> {
                onDataAvailable(intent.getStringExtra(Constants.EXTRA_DATA))
            }
        }
    }


}