package com.whoissio.eving.viewmodels

import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.adapters.MyIotRecyclerAdapter
import com.whoissio.eving.adapters.NewIotRecyclerAdapter
import com.whoissio.eving.networks.services.IotService
import com.whoissio.eving.utils.Helpers.toDisposal

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

    init {
        tryFetchMyIotDevices()
    }
}