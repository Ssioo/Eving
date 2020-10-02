package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.R
import com.whoissio.eving.models.UserGender
import com.whoissio.eving.networks.services.UserService
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.SingleEvent
import com.whoissio.eving.utils.Validators.isValidBirth
import com.whoissio.eving.utils.Validators.isValidEmail
import com.whoissio.eving.utils.Validators.isValidPw

class SignUpViewModel : BaseViewModel() {

    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    val birth: MutableLiveData<String> = MutableLiveData("")
    val gender: MutableLiveData<UserGender> = MutableLiveData(UserGender.MALE)

    fun onClickGender(gender: UserGender) {
        this.gender.value = gender
    }

    fun onClickSubmit() {
        if (!email.value.isValidEmail() || !pw.value.isValidPw() || !birth.value.isValidBirth()) {
            alertEvent.value = SingleEvent(data = R.string.sign_in_input_error)
            return
        }
        networkEvent.startLoading()
        UserService().tryRegister(email.value!!, pw.value!!, gender.value!!, birth.value!!)
            .toDisposal(rxDisposable, {
                networkEvent.handleResponse(it)
                it?.let {
                    alertEvent.value =
                        SingleEvent(data = if (it.code == 200) R.string.sign_up_success else R.string.sign_in_input_error)
                }
            }, {
                doOnNetworkError(it)
            })
    }
}