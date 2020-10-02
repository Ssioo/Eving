package com.whoissio.eving.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivitySplashBinding
import com.whoissio.eving.viewmodels.SplashViewModel

class SplashActivity(override val layoutId: Int = R.layout.activity_splash) : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun getViewModel(): SplashViewModel =
        ViewModelProvider(this).get(SplashViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }


}