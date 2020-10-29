package com.whoissio.eving.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.models.User
import com.whoissio.eving.networks.services.UserService
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.utils.Helpers.toDisposal

class MainViewModel : BaseViewModel() {

    val visibleFragment: MutableLiveData<String> = MutableLiveData(Constants.FRAGMENT_CONTENTS)
    val me: MutableLiveData<User> = MutableLiveData()
    val email = Transformations.map(me) { it.email }

    private fun tryFetchUserInfo() {
        UserService().getProfile().toDisposal(rxDisposable, {
            it.data?.let { me.value = it }
        }, { doOnNetworkError(it) })
    }

    init {
        tryFetchUserInfo()
    }
}