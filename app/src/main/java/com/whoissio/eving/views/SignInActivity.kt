package com.whoissio.eving.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivitySignInBinding
import com.whoissio.eving.viewmodels.SignInViewModel

class SignInActivity(override val layoutId: Int = R.layout.activity_sign_in): BaseActivity<ActivitySignInBinding, SignInViewModel>() {

    override fun getViewModel(): SignInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

    }
}