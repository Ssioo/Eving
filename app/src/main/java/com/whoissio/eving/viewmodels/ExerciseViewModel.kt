package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel

class ExerciseViewModel: BaseViewModel() {

    var isRecording: MutableLiveData<Boolean> = MutableLiveData(false)

    fun toggleRecording() {
        isRecording.value = !isRecording.value!!
    }
}