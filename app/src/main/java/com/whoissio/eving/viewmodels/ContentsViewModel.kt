package com.whoissio.eving.viewmodels

import androidx.lifecycle.MutableLiveData
import com.whoissio.eving.BaseViewModel
import com.whoissio.eving.adapters.AdsRecyclerAdapter
import com.whoissio.eving.adapters.ContentsRecyclerAdapter
import com.whoissio.eving.models.Ads
import com.whoissio.eving.models.Contents
import com.whoissio.eving.networks.services.ContentsService
import com.whoissio.eving.utils.Helpers.toDisposal
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ContentsViewModel : BaseViewModel() {

    val adsRecyclerAdapter = AdsRecyclerAdapter(viewModel = this)
    val contentsRecyclerAdapter = ContentsRecyclerAdapter(viewModel = this)

    val ads: MutableLiveData<ArrayList<Ads?>> = MutableLiveData(ArrayList())
    val contents: MutableLiveData<ArrayList<Contents?>> = MutableLiveData(ArrayList())

    val curAdsIdx: MutableLiveData<Int> = MutableLiveData(0)

    fun tryFetchContents() {
        networkEvent.startLoading()
        ContentsService().fetchAllContents().toDisposal(rxDisposable, {
            networkEvent.handleResponse(it)
            it?.let {
                if (it.code != 200) {
                    return@let
                }
                it.data?.let { contents.value = it }
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
                it.data?.let { ads.value = it }
            }
        }, { doOnNetworkError(it) })
    }

    fun registerAdsLoop() {
        rxDisposable.add(
            Observable
                .interval(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    curAdsIdx.value = (curAdsIdx.value!! + 1) % (ads.value?.size ?: 1)
                }, {
                    it.printStackTrace()
                })
        )
    }


    init {
        tryFetchContents()
        tryFetchAds()
    }
}