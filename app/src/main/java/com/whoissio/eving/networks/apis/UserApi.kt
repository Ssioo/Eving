package com.whoissio.eving.networks.apis

import com.whoissio.eving.models.Jwt
import com.whoissio.eving.models.UserRegisterParam
import com.whoissio.eving.models.UserTokenIssueParam
import com.whoissio.eving.networks.BaseResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("/eving/users")
    fun registerUser(@Body param: UserRegisterParam): Single<BaseResponse<Any>>

    @POST("/eving/users/token")
    fun issueToken(@Body param: UserTokenIssueParam): Single<BaseResponse<Jwt>>

    @GET("/eving/users/token/verify")
    fun verifyToken(): Single<BaseResponse<Any>>
}