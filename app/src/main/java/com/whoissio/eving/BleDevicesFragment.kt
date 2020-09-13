package com.whoissio.eving

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context.BLUETOOTH_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_ble_devices.*

class BleDevicesFragment: Fragment(R.layout.fragment_ble_devices) {

    companion object {
        const val REQUEST_ENABLE_BT = 100
        const val REQUEST_FINE_LOCATION = 101
        const val SCAN_PERIOD = 5000L
    }

    private val bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().bluetoothLeScanner
    private var mScanning = false
    private val handler = Handler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_bles.adapter = leDeviceListAdapter
        btn_edit_bles.setOnClickListener {
            MaterialAlertDialogBuilder(activity!!)
                .setMessage("디바이스를 편집할까요?")
                .setPositiveButton("확인", { dialog, which ->
                    dialog.dismiss()
                })
                .create()
                .show()
        }
        btn_find_ble.setOnClickListener { scanLeDevice() }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.BLUETOOTH
                ),
                REQUEST_FINE_LOCATION
            )
        } else {
            val bluetoothManager = context?.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager?
            val bluetoothAdapter = bluetoothManager?.adapter
            if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            } else {
                scanLeDevice()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT && resultCode == AppCompatActivity.RESULT_OK) {
            scanLeDevice()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_FINE_LOCATION && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            val bluetoothManager = context?.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager?
            val bluetoothAdapter = bluetoothManager?.adapter
            if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            } else {
                scanLeDevice()
            }
        }
    }

    private fun scanLeDevice() {
        if (!mScanning) {
            leDeviceListAdapter.clear()
            handler.postDelayed({
                mScanning = false
                bluetoothLeScanner.stopScan(leScanCallback)
            }, SCAN_PERIOD)
            mScanning = true
            bluetoothLeScanner.startScan(leScanCallback)
        } else {
            mScanning = false
            bluetoothLeScanner.stopScan(leScanCallback)
        }
    }

    private val leDeviceListAdapter = NewLeDeviceRecyclerAdapter()
    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            Log.d("DeviceName", result?.device.toString())
            leDeviceListAdapter.addDevice(result?.device)
        }
    }
}