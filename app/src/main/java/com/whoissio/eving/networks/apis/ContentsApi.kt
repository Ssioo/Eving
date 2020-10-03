package com.whoissio.eving.networks.apis

import com.whoissio.eving.models.Ads
import com.whoissio.eving.models.Contents
import com.whoissio.eving.networks.BaseResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ContentsApi {
    @GET("/eving/contents")
    fun getAllContents(): Single<BaseResponse<ArrayList<Contents>>>

    @GET("/eving/contents/ads")
    fun getAllAds(): Single<BaseResponse<ArrayList<Ads>>>
}