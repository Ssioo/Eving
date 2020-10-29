package com.whoissio.eving.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseFragment
import com.whoissio.eving.R
import com.whoissio.eving.databinding.FragmentExerciseResultBinding
import com.whoissio.eving.utils.SensorType
import com.whoissio.eving.viewmodels.ExerciseResultViewModel
import com.whoissio.eving.viewmodels.ExerciseViewModel

class ExerciseResultFragment: BaseFragment<FragmentExerciseResultBinding, ExerciseResultViewModel , ExerciseViewModel>(R.layout.fragment_exercise_result) {
    override fun getViewModel(): ExerciseResultViewModel = ViewModelProvider(this).get(ExerciseResultViewModel::class.java)

    override fun getParentViewModel(): ExerciseViewModel = ViewModelProvider(requireActivity()).get(ExerciseViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        vm.visibleSensorType.observe(this) {
            binding.chartExercise.apply {
                data?.dataSets?.apply {
                    forEachIndexed { idx, _ ->
                        get(idx).isVisible = it.chartIdx == idx
                    }
                }
                axisRight.axisMinimum = if (it.type == SensorType.TILT.type) 0f else -360f
                invalidate()
            }
        }
    }
}