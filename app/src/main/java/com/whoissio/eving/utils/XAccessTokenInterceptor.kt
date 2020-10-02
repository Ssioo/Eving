package com.whoissio.eving.utils

import com.whoissio.eving.ApplicationClass.Companion.sSharedPreferences
import com.whoissio.eving.utils.Constants.X_ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class XAccessTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString(X_ACCESS_TOKEN, null)
        if (jwtToken != null) {
            builder.addHeader("Authorization", "Token $jwtToken")
        }
        return chain.proceed(builder.build())
    }
}