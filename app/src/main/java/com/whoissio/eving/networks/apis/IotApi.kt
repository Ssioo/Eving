package com.whoissio.eving.networks.apis

import com.whoissio.eving.models.IotDevice
import com.whoissio.eving.networks.BaseResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface IotApi {
    @GET("/eving/iot/devices")
    fun getAllDevices(): Single<BaseResponse<ArrayList<IotDevice>>>

    @POST("/eving/iot/devices")
    fun registerDevice(@Body param: Any): Single<BaseResponse<Any>>

    @DELETE("/eving/iot/devices/{deviceId}")
    fun deleteDevice(@Path("deviceId") deviceId: Int): Single<BaseResponse<Any>>
}