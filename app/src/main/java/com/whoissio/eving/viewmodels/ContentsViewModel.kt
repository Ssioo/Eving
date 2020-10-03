package com.whoissio.eving.viewmodels

import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.adapters.ContentsRecyclerAdapter
import com.whoissio.eving.networks.services.ContentsService
import com.whoissio.eving.utils.Helpers.toDisposal

class ContentsViewModel: BaseViewModel() {

    val contentsRecyclerAdapter = ContentsRecyclerAdapter(viewModel = this)

    fun tryFetchContents() {
        networkEvent.startLoading()
        ContentsService().fetchAllContents().toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) {
                    return@let
                }
                it.data?.let { contentsRecyclerAdapter.setItem(it) }
            }
        }, {
            doOnNetworkError(it)
        })
    }

    init {
        tryFetchContents()
    }
}