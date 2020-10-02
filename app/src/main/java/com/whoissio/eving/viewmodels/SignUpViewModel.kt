package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel

class SignUpViewModel: BaseViewModel() {

    val email: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    val birth: MutableLiveData<String> = MutableLiveData("")
    val gender: MutableLiveData<Int> = MutableLiveData(1)

    fun onClickGender(gender: Int) {
        this.gender.value = when (gender) {
            1 -> 2
            else -> 1
        }
    }

    fun onClickSubmit() {

    }
}