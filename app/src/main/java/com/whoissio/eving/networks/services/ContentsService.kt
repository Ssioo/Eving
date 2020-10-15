package com.whoissio.eving.networks.services

import com.whoissio.eving.ApplicationClass
import com.whoissio.eving.models.Ads
import com.whoissio.eving.models.Contents
import com.whoissio.eving.networks.BaseResponse
import com.whoissio.eving.networks.apis.ContentsApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ContentsService {
    fun fetchAllContents(): Single<BaseResponse<ArrayList<Contents>>> {
        return ApplicationClass.retrofit.create(ContentsApi::class.java)
            .getAllContents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchAllAds(): Single<BaseResponse<ArrayList<Ads>>> {
        return ApplicationClass.retrofit.create(ContentsApi::class.java)
            .getAllAds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}