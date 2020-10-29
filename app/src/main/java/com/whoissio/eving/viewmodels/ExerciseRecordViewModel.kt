package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.adapters.ExerciseListAdapter
import com.whoissio.eving.models.Exercise
import com.whoissio.eving.networks.services.ExerciseService
import com.whoissio.eving.utils.Helpers.toDisposal

class ExerciseRecordViewModel : BaseViewModel() {

    val exercises: MutableLiveData<ArrayList<Exercise?>> = MutableLiveData(ArrayList())

    val sort: MutableLiveData<Boolean> = MutableLiveData(false)

    val exerciseListAdapter = ExerciseListAdapter(this)

    fun fetchExercises() {
        networkEvent.startLoading()
        ExerciseService().getAllExercises().toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) return@let
                it.data?.let { exercises.value = it }
            }
        }, {
            doOnNetworkError(it)
        })
    }

    fun toggleSort() {
        sort.value = !sort.value!!
    }

    init {
        fetchExercises()
    }
}