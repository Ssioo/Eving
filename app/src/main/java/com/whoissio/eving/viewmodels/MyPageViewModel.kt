package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.R
import com.whoissio.eving.networks.services.UserService
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.objects.SingleEvent

class MyPageViewModel: BaseViewModel() {

    val finish: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()

    fun tryResign() {
        networkEvent.startLoading()
        UserService().resign().toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            if (it.code == 200) {
                finish.value = SingleEvent(data = true)
            }
                else alertEvent.value = SingleEvent(data = R.string.network_error)
        }, { doOnNetworkError(it) })
    }
}