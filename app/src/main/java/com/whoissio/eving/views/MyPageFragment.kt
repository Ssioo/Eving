package com.whoissio.eving.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.ApplicationClass
import com.whoissio.eving.BaseFragment
import com.whoissio.eving.R
import com.whoissio.eving.databinding.FragmentMypageBinding
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.utils.components.PosNegDialog
import com.whoissio.eving.utils.components.SimpleMessageDialog
import com.whoissio.eving.viewmodels.MainViewModel
import com.whoissio.eving.viewmodels.MyPageViewModel

class MyPageFragment(override val layoutId: Int = R.layout.fragment_mypage) :
    BaseFragment<FragmentMypageBinding, MyPageViewModel, MainViewModel>(layoutId) {

    override fun getViewModel(): MyPageViewModel =
        ViewModelProvider(this).get(MyPageViewModel::class.java)

    override fun getParentViewModel(): MainViewModel =
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

        binding.tvRegisterdDevices.setOnClickListener {
            startActivity(Intent(activity, IotActivity::class.java))
        }

        binding.tvMyExercises.setOnClickListener {
            startActivity(Intent(activity, ExerciseRecordActivity::class.java))
        }

        binding.tvLogout.setOnClickListener {
            PosNegDialog(requireActivity(), "로그아웃하시겠습니까?") { dialog, which ->
                dialog.dismiss()
                if (which == PosNegDialog.BUTTON_POSITIVE) {
                    ApplicationClass.sSharedPreferences.edit().remove(Constants.X_ACCESS_TOKEN)
                        .apply()
                    activity?.finish()
                    startActivity(Intent(activity, SplashActivity::class.java))
                }
            }.show()
        }

        binding.tvResign.setOnClickListener {
            PosNegDialog(requireActivity(), "회원탈퇴하시겠습니까?\n기존 데이터는 복구되지 않습니다.", ) { dialog, which ->
                if (which == PosNegDialog.BUTTON_POSITIVE)
                    vm.tryResign()
                dialog.dismiss()
            }.show()
        }

        binding.tvFindPw.setOnClickListener {
            showToast(getString(R.string.not_implemented))
        }

        binding.tvPolicies.setOnClickListener {
            showToast(getString(R.string.not_implemented))
        }

        binding.tvHelp.setOnClickListener {
            showToast(getString(R.string.not_implemented))
        }

        binding.tvFeedback.setOnClickListener {
            showToast(getString(R.string.not_implemented))
        }

        vm.finish.observe(this) {
            it.get()?.let {
                if (it) {
                    ApplicationClass.sSharedPreferences.edit().remove(Constants.X_ACCESS_TOKEN)
                        .apply()
                    activity?.finish()
                    startActivity(Intent(activity, SplashActivity::class.java))
                }
            }
        }
    }
}