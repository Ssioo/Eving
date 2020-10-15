package com.whoissio.eving.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivitySplashBinding
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.viewmodels.SplashViewModel

class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {

    override fun getViewModel(): SplashViewModel =
        ViewModelProvider(this).get(SplashViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        viewmodel.tryAutoSignIn()

        viewmodel.moveTo.observe(this, {
            it.get()?.let {
                startActivity(
                    Intent(
                        this, when (it) {
                            Constants.ACTIVITY_MAIN -> MainActivity::class.java
                            else -> SignInActivity::class.java
                        }
                    )
                )
            }
        })
    }
}