package com.whoissio.eving.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.whoissio.eving.BaseActivity
import com.whoissio.eving.R
import com.whoissio.eving.databinding.ActivityMainBinding
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.utils.SingleEvent
import com.whoissio.eving.viewmodels.MainViewModel

class MainActivity(override val layoutId: Int = R.layout.activity_main)
    : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getViewModel(): MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding?.bnvMain?.setOnNavigationItemSelectedListener {
            viewmodel.visibleFragment.value = when(it.itemId) {
                R.id.menu_home -> Constants.FRAGMENT_CONTENTS
                R.id.menu_search -> Constants.FRAGMENT_SEARCH
                R.id.menu_mypage -> Constants.FRAGMENT_MYPAGE
                else -> Constants.FRAGMENT_CONTENTS
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

}
