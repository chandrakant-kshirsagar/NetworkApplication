package com.devrex.network.retrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RestApiInterface {

    @POST("{endPoint}")
    fun callPostApi(
        @Path(value = "endPoint", encoded = true) endPoint: String?,
        @HeaderMap headers: HashMap<String, String?>? = null,
        @Body params: RequestBody?,
        @QueryMap paramsMap: HashMap<String, String>? = null,
    ): Call<ResponseBody>

    @GET("{endPoint}")
    fun callGetApi(
        @Path(value = "endPoint", encoded = true) endPoint: String?,
        @QueryMap paramsMap: HashMap<String, String>? = null,
    ): Call<ResponseBody>
}