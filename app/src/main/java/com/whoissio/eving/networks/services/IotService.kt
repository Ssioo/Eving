package com.whoissio.eving.networks.services

import com.whoissio.eving.ApplicationClass
import com.whoissio.eving.models.IotDevice
import com.whoissio.eving.models.IotRegisterParam
import com.whoissio.eving.models.IotType
import com.whoissio.eving.networks.BaseResponse
import com.whoissio.eving.networks.apis.IotApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class IotService {

    fun fetchMyIotDevices(): Single<BaseResponse<ArrayList<IotDevice>>> {
        return ApplicationClass.retrofit.create(IotApi::class.java)
            .getAllDevices()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun registerIotDevice(address: String, uuid: String? = null, name: String? = null, type: IotType = IotType.CUSTOM): Single<BaseResponse<Any>> {
        return ApplicationClass.retrofit.create(IotApi::class.java)
            .registerDevice(IotRegisterParam(uuid, name, address, type))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteMyIotDevice(deviceId: Int): Single<BaseResponse<Any>> {
        return ApplicationClass.retrofit.create(IotApi::class.java)
            .deleteDevice(deviceId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}