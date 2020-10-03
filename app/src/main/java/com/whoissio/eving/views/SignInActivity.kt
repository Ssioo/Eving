package com.whoissio.eving.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivitySignInBinding
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.viewmodels.SignInViewModel

class SignInActivity(override val layoutId: Int = R.layout.activity_sign_in) :
    BaseActivity<ActivitySignInBinding, SignInViewModel>() {

    override fun getViewModel(): SignInViewModel =
        ViewModelProvider(this).get(SignInViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

        binding?.btnFindEmail?.setOnClickListener {
            showToast(getString(R.string.not_implemented))
        }

        binding?.btnFindPw?.setOnClickListener {
            showToast(getString(R.string.not_implemented))
        }

        binding?.btnSignUp?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        viewmodel.moveTo.observe(this, {
            it.get()?.let {
                if (it == Constants.ACTIVITY_MAIN) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        })
    }
}