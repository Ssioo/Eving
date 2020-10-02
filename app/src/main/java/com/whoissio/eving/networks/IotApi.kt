package com.whoissio.eving.networks

import com.whoissio.eving.models.IotDevice
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface IotApi {
    @GET("/eving/devices")
    fun getAllDevices(): Single<BaseResponse<Any>>

    @POST("/eving/devices")
    fun registerDevice(@Body param: Any): Single<BaseResponse<IotDevice>>

    @DELETE("/eving/devices/{deviceId}")
    fun registerDevice(@Path("deviceId") deviceId: Int): Single<BaseResponse<Any>>
}