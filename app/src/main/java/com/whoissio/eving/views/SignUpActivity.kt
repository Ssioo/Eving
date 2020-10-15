package com.whoissio.eving.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivitySignUpBinding
import com.whoissio.eving.viewmodels.SignUpViewModel

class SignUpActivity:
    BaseActivity<ActivitySignUpBinding, SignUpViewModel>(R.layout.activity_sign_up) {

    override fun getViewModel(): SignUpViewModel =
        ViewModelProvider(this).get(SignUpViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

    }
}