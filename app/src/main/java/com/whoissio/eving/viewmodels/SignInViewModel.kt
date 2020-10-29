package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.ApplicationClass
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.R
import com.whoissio.eving.networks.services.UserService
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.objects.SingleEvent
import com.whoissio.eving.utils.Validators.isValidEmail
import com.whoissio.eving.utils.Validators.isValidPw

class SignInViewModel : BaseViewModel() {

    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    val moveTo: MutableLiveData<SingleEvent<String>> = MutableLiveData()

    fun onClickSignIn() {
        if (!email.value.isValidEmail() || !pw.value.isValidPw()) {
            alertEvent.value = SingleEvent(data = R.string.sign_in_input_error)
            return
        }
        trySignIn(email.value!!, pw.value!!)
    }

    fun trySignIn(email: String, pw: String) {
        networkEvent.startLoading()
        UserService().signIn(email, pw)
            .toDisposal(rxDisposable, {
                networkEvent.handleResponse(it)
                if (it.code != 200 || it.data == null) {
                    alertEvent.value = SingleEvent(data = R.string.sign_in_input_error)
                    return@toDisposal
                }
                toastEvent.value = SingleEvent(data = R.string.sign_in_success)
                ApplicationClass.sSharedPreferences.edit()
                    .putString(Constants.X_ACCESS_TOKEN, it.data.token).apply()
                moveTo.value = SingleEvent(Constants.ACTIVITY_MAIN)
            }, {
                doOnNetworkError(it)
            })
    }
}