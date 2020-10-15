package com.whoissio.eving.views

import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivityExerciseBinding
import com.whoissio.eving.utils.BleGattService
import com.whoissio.eving.utils.BleUpdateHandler
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.viewmodels.ExerciseViewModel

class ExerciseActivity :
    BaseActivity<ActivityExerciseBinding, ExerciseViewModel>(R.layout.activity_exercise), ServiceConnection {

    private lateinit var bleServiceIntent: Intent
    private var bleService: BleGattService? = null
    private val bleUpdateHandler = BleUpdateHandler(({

    },{

    }, {

    }, {

    })

    override fun getViewModel(): ExerciseViewModel =
        ViewModelProvider(this).get(ExerciseViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        bindService(Intent(this, BleGattService::class.java).also { bleServiceIntent = it }, this, Context.BIND_AUTO_CREATE)
        startService(bleServiceIntent)

        binding?.chartExercise?.apply {
            data = LineData(ArrayList<ILineDataSet>().apply {
                add(
                    LineDataSet(
                        ArrayList<Entry>().apply {
                            add(Entry(0f, 50f))
                        },
                        ""
                    )
                )
            })
            invalidate()
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(bleUpdateHandler, IntentFilter().apply {
            addAction(Constants.ACTION_DATA_AVAILABLE)
            addAction(Constants.ACTION_GATT_CONNECTED)
            addAction(Constants.ACTION_GATT_DISCONNECTED)
            addAction(Constants.ACTION_GATT_SERVICES_DISCOVERED)
        })
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(bleUpdateHandler)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(bleServiceIntent)
        unbindService(this)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        bleService = (service as BleGattService.BleServiceBinder).getService()
        //bleService.connect("")
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        bleService = null
    }
}