package com.whoissio.eving.views

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.orhanobut.logger.Logger
import com.polidea.rxandroidble2.RxBleClient
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivityExerciseBinding
import com.whoissio.eving.models.Sensor
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.utils.objects.SingleEvent
import com.whoissio.eving.viewmodels.ExerciseViewModel
import io.reactivex.disposables.CompositeDisposable
import java.nio.ByteBuffer

class ExerciseActivity :
    BaseActivity<ActivityExerciseBinding, ExerciseViewModel>(R.layout.activity_exercise) {

    override fun getViewModel(): ExerciseViewModel =
        ViewModelProvider(this).get(ExerciseViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

        setSupportActionBar(binding?.tbExercise)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back_white)
            setDisplayShowTitleEnabled(false)
        }

        viewmodel.canFinish.observe(this) { it.get()?.let { if (it) finish() }}
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (!findNavController(R.id.nav_exercise).popBackStack()) finish()
        }
        return super.onOptionsItemSelected(item)
    }
}