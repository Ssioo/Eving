package com.whoissio.eving.networks

import androidx.lifecycle.MutableLiveData

class NetworkEvent: MutableLiveData<NetworkEvent.NetworkState>() {

    enum class NetworkState {
        LOADING, FAILURE, SUCCESS, ERROR
    }

    fun startLoading() {
        value = NetworkState.LOADING
    }

    fun handleResponse2(response : BaseResponse<*>?) {
        value = response?.let {
            if (it.code.toString().startsWith("4") || it.code.toString().startsWith("5")) NetworkState.SUCCESS
            else NetworkState.FAILURE
        } ?:  NetworkState.ERROR
    }
}