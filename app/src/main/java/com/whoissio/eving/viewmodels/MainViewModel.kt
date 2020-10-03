package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.utils.Constants

class MainViewModel : BaseViewModel() {

    val visibleFragment: MutableLiveData<String> = MutableLiveData(Constants.FRAGMENT_CONTENTS)
}