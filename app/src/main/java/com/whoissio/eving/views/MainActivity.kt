package com.whoissio.eving.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivityMainBinding
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.viewmodels.MainViewModel

class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override fun getViewModel(): MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding?.bnvMain?.setOnNavigationItemSelectedListener {
            viewmodel.visibleFragment.value = when(it.itemId) {
                R.id.menu_home -> Constants.FRAGMENT_CONTENTS
                R.id.menu_search -> {
                    startActivity(Intent(this, ExerciseActivity::class.java))
                    return@setOnNavigationItemSelectedListener false
                }
                R.id.menu_mypage -> Constants.FRAGMENT_MYPAGE
                else -> Constants.FRAGMENT_CONTENTS
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

}
