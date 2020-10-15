package com.whoissio.eving.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseFragment
import com.whoissio.eving.R
import com.whoissio.eving.databinding.FragmentMypageBinding
import com.whoissio.eving.viewmodels.MyPageViewModel

class MyPageFragment(override val layoutId: Int = R.layout.fragment_mypage) :
    BaseFragment<FragmentMypageBinding, MyPageViewModel>(layoutId) {
    override fun getViewModel(): MyPageViewModel =
        ViewModelProvider(this).get(MyPageViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

        binding?.tvRegisterdDevices?.setOnClickListener {
            startActivity(Intent(activity, IotActivity::class.java))
        }
    }
}