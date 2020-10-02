package com.whoissio.eving.networks

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.whoissio.eving.utils.SingleEvent

class NetworkEvent: MutableLiveData<NetworkEvent.NetworkState>() {

    enum class NetworkState {
        LOADING, FAILURE, SUCCESS, ERROR
    }

    fun startLoading() {
        value = NetworkState.LOADING
    }

    fun handleResponse(response : BaseResponse<*>?) {
        value = response?.let {
            if (it.code.toString().startsWith("4") || it.code.toString().startsWith("5")) NetworkState.SUCCESS
            else NetworkState.FAILURE
        } ?:  NetworkState.ERROR
    }
}