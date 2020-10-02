package com.whoissio.eving.networks

import com.google.gson.annotations.SerializedName

class BaseResponse<T> (
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: T?,
    @SerializedName("msg") val message: String?
)