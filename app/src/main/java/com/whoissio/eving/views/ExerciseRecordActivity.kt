package com.whoissio.eving.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivityExerciseRecordBinding
import com.whoissio.eving.viewmodels.ExerciseRecordViewModel
import java.text.SimpleDateFormat
import java.util.*

class ExerciseRecordActivity: BaseActivity<ActivityExerciseRecordBinding, ExerciseRecordViewModel>(R.layout.activity_exercise_record) {

    override fun getViewModel(): ExerciseRecordViewModel = ViewModelProvider(this).get(ExerciseRecordViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

        setSupportActionBar(binding?.tbExerciseRecord)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_back_white)
            setDisplayHomeAsUpEnabled(true)
        }

        binding?.rvExerciseRecords?.adapter = viewmodel.exerciseListAdapter

        viewmodel.exercises.observe(this) {
            viewmodel.exerciseListAdapter.setItems(it)
        }

        /*viewmodel.sort.observe(this) {
            *//*if (it) {
                viewmodel.exercises.value?.sortBy { SimpleDateFormat("YYYY-MM-DD`T`HH:mm:ss.SSS`Z`", Locale.getDefault()).parse(it?.createdAt!!) }
            } else {
                viewmodel.exercises.value?.sortBy { it?.id!! }
            }*//*
            viewmodel.exerciseListAdapter.setItems(viewmodel.exercises.value!!)
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}