package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.networks.services.UserService
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.SingleEvent

class SplashViewModel : BaseViewModel() {

    val moveTo: MutableLiveData<SingleEvent<String>> = MutableLiveData()

    fun tryAutoSignIn() {
        UserService().tryVerifyToken().toDisposal(rxDisposable, {
            it?.let {
                moveTo.value =
                    SingleEvent(data = if (it.code == 200) Constants.ACTIVITY_MAIN else Constants.ACTIVITY_SIGN_IN)
            }
        }, {
            doOnNetworkError(it)
            moveTo.value = SingleEvent(data = Constants.ACTIVITY_SIGN_IN)
        })
    }
}