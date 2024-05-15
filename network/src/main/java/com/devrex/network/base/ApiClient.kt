package com.devrex.network.base

import com.devrex.network.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    fun getRetrofitApiClient(
        baseUrl: String,
        timeOut: Long,
    ): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ToStringConverterFactory())
            .client(getHttpClient(timeOut))
            .build()
    }

    private fun getHttpClient(timeOut: Long): OkHttpClient {
        val builder = OkHttpClient.Builder()
        /*builder.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()

            if (headers.isNotEmpty()) {
                for ((key, value) in headers) {
                    value?.let {
                        requestBuilder.addHeader(key, value)
                    }
                }
            }
            requestBuilder.method(original.method, original.body)
            val request = requestBuilder.build()
            it.proceed(request)
        }*/

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            builder.addInterceptor(httpLoggingInterceptor)
        }
        builder.readTimeout(timeOut, TimeUnit.MILLISECONDS)
        builder.writeTimeout(timeOut, TimeUnit.MILLISECONDS)
        builder.connectTimeout(timeOut, TimeUnit.MILLISECONDS)
        return builder.build()
    }
}