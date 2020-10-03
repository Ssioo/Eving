package com.whoissio.eving.utils

import android.content.Context
import android.util.TypedValue
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

    fun Float.toPx(context: Context): Float =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            context.resources.displayMetrics
        )

    fun Int.toPx(context: Context): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            context.resources.displayMetrics
        ).toInt()
}