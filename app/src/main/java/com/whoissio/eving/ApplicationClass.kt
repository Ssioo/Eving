package com.whoissio.eving

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.whoissio.eving.utils.Constants
import com.whoissio.eving.utils.Constants.SP_TAG
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {

    companion object {

        lateinit var sSharedPreferences: SharedPreferences

        val httpClient = OkHttpClient.Builder()
            .readTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    if (message.startsWith("{") && message.endsWith("}")) {
                        Logger.t("OKHTTP").json(message)
                    } else {
                        Log.i("OKHTTP", message)
                    }
                }
            }))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences = applicationContext.getSharedPreferences(SP_TAG, Context.MODE_PRIVATE)

        Logger.addLogAdapter(object : AndroidLogAdapter(
            PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .build()) {
            override fun isLoggable(priority: Int, tag: String?): Boolean = BuildConfig.DEBUG
        })
    }
}