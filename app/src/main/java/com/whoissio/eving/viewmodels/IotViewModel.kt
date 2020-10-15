package com.whoissio.eving.viewmodels

import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.R
import com.whoissio.eving.adapters.MyIotRecyclerAdapter
import com.whoissio.eving.adapters.NewIotRecyclerAdapter
import com.whoissio.eving.networks.services.IotService
import com.whoissio.eving.utils.Helpers.toDisposal
import com.whoissio.eving.utils.SingleEvent

class IotViewModel: BaseViewModel() {
    val myIotAdapter = MyIotRecyclerAdapter(viewModel = this)
    val newIotRecyclerAdapter = NewIotRecyclerAdapter()

    fun tryFetchMyIotDevices() {
        networkEvent.startLoading()
        IotService().fetchMyIotDevices().toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) {
                    return@let
                }
                it.data?.let { myIotAdapter.setItem(it) }
            }
        }, {
            doOnNetworkError(it)
        })
    }

    fun deleteMyIotDevice(deviceId: Int) {
        networkEvent.startLoading()
        IotService().deleteMyIotDevice(deviceId).toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) {
                    alertEvent.value = SingleEvent(data = R.string.network_error)
                    return@let
                }
            }
        }, { doOnNetworkError(it) })
    }

    init {
        tryFetchMyIotDevices()
    }
}