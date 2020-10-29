package com.whoissio.eving.views

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivityIotsBinding
import com.whoissio.eving.networks.services.IotService
import com.whoissio.eving.viewmodels.IotViewModel
import kotlinx.android.synthetic.main.activity_iots.*

class IotActivity
    : BaseActivity<ActivityIotsBinding, IotViewModel>(R.layout.activity_iots) {
    companion object {
        const val REQUEST_ENABLE_BT = 100
        const val REQUEST_FINE_LOCATION = 101
        const val SCAN_PERIOD = 5000L
    }

    private val bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().bluetoothLeScanner
    private var mScanning = false
    private val handler = Handler()
    val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            Log.d("DeviceName", result.toString())
            viewmodel.newIotRecyclerAdapter.addDevice(result?.device)
        }
    }

    override fun getViewModel(): IotViewModel {
        return ViewModelProvider(this).get(IotViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {

        setSupportActionBar(binding?.tbIots)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_back_white)
            setDisplayHomeAsUpEnabled(true)
        }

        rv_bles.adapter = viewmodel.newIotRecyclerAdapter
        rv_my_bles.adapter = viewmodel.myIotAdapter
        btn_edit_bles.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage("디바이스를 편집할까요?")
                .setPositiveButton("확인", { dialog, which ->
                    if (which == DialogInterface.BUTTON_POSITIVE) {

                    }
                    dialog.dismiss()
                })
                .create()
                .show()
        }
        btn_find_ble.setOnClickListener { scanLeDevice() }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.BLUETOOTH
                ),
                REQUEST_FINE_LOCATION
            )
        } else {
            tryScanIotDevices()
        }

        viewmodel.myIots.observe(this) { viewmodel.myIotAdapter.setItems(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
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
            tryScanIotDevices()
        }
    }

    private fun tryScanIotDevices() {
        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager?
        val bluetoothAdapter = bluetoothManager?.adapter
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            scanLeDevice()
        }
    }

    private fun scanLeDevice() {
        if (!mScanning) {
            viewmodel.newIotRecyclerAdapter.clear()
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
}