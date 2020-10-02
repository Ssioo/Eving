package com.whoissio.eving.networks.services

import com.whoissio.eving.ApplicationClass
import com.whoissio.eving.models.Jwt
import com.whoissio.eving.models.UserGender
import com.whoissio.eving.models.UserRegisterParam
import com.whoissio.eving.models.UserTokenIssueParam
import com.whoissio.eving.networks.BaseResponse
import com.whoissio.eving.networks.apis.UserApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class UserService {
    fun tryRegister(email: String, pwd: String, gender: UserGender, birth: String): Single<BaseResponse<Any>> {
        return ApplicationClass.retrofit.create(UserApi::class.java)
            .registerUser(UserRegisterParam(email, pwd, birth, gender))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun trySignIn(email: String, pwd: String): Single<BaseResponse<Jwt>> {
        return ApplicationClass.retrofit.create(UserApi::class.java)
            .issueToken(UserTokenIssueParam(email, pwd))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}