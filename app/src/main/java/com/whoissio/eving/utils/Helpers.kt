package com.whoissio.eving.utils

import com.whoissio.eving.networks.BaseResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer

object Helpers {
    fun <T> Single<BaseResponse<T>>.toDisposal(
        disposable: CompositeDisposable,
        onSuccess: Consumer<in BaseResponse<T>>,
        onError: Consumer<in Throwable>
    ) {
        disposable.add(this.subscribe(onSuccess, onError))
    }
}