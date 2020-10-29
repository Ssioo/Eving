package com.whoissio.eving

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whoissio.eving.networks.NetworkEvent
import com.whoissio.eving.utils.objects.SingleEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val rxDisposable = CompositeDisposable()

    val networkEvent = NetworkEvent()
    val toastEvent: MutableLiveData<SingleEvent<Int>> = MutableLiveData()
    val alertEvent: MutableLiveData<SingleEvent<Int>> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        if (!rxDisposable.isDisposed) rxDisposable.dispose()
    }

    open fun doOnNetworkError(error: Throwable?) {
        error?.printStackTrace()
        networkEvent.value = NetworkEvent.NetworkState.ERROR
        alertEvent.value = SingleEvent(data = R.string.network_error)
    }
}