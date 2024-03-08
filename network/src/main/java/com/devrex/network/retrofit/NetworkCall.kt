package com.devrex.network.retrofit

import com.devrex.network.base.Resource

class NetworkCall {
    companion object {
        const val DEFAULT_TIME = (60 * 1000).toLong()
    }
    @JvmOverloads
    suspend inline fun <reified T> callPost(
        baseUrl: String,
        endPoint: String,
        headers: HashMap<String, String?>,
        postParams: String,
        queryMap: HashMap<String, String>?,
        timeOut: Long = DEFAULT_TIME,
    ): Resource<T> {
        return SafeRestCallBack().callPostApi(
            baseUrl, endPoint, headers, postParams, queryMap, timeOut
        )
    }

    @JvmOverloads
    suspend inline fun <reified T> callGet(
        baseUrl: String,
        endPoint: String,
        queryMap: HashMap<String, String>?,
        timeOut: Long = DEFAULT_TIME,
    ): Resource<T> {
        return SafeRestCallBack().callGetApi(
            baseUrl, endPoint, queryMap, timeOut
        )
    }
}