package com.whoissio.eving.networks

import com.whoissio.eving.models.Jwt
import com.whoissio.eving.models.User
import com.whoissio.eving.models.UserRegisterParam
import com.whoissio.eving.models.UserTokenIssueParam
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/eving/users")
    fun registerUser(@Body param: UserRegisterParam): Single<BaseResponse<User>>

    @POST("/eving/users/token")
    fun issueToken(@Body param: UserTokenIssueParam): Single<BaseResponse<Jwt>>
}