package com.whoissio.eving.viewmodels

import androidx.recyclerview.widget.ConcatAdapter
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.adapters.AdsRecyclerAdapter
import com.whoissio.eving.adapters.ContentsRecyclerAdapter
import com.whoissio.eving.networks.services.ContentsService
import com.whoissio.eving.utils.Helpers.toDisposal

class ContentsViewModel: BaseViewModel() {

    val adsRecyclerAdapter = AdsRecyclerAdapter(viewModel = this)
    val contentsRecyclerAdapter = ContentsRecyclerAdapter(viewModel = this)
    val adsAndContentsAdapter: ConcatAdapter = ConcatAdapter(adsRecyclerAdapter, contentsRecyclerAdapter)

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

    fun tryFetchAds() {
        networkEvent.startLoading()
        ContentsService().fetchAllAds().toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) {
                    return@let
                }
                it.data?.let { adsRecyclerAdapter.setItem(it) }
            }
        }, { doOnNetworkError(it) })
    }

    init {
        tryFetchContents()
        tryFetchAds()
    }
}