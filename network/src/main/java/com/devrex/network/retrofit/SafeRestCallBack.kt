package com.devrex.network.retrofit

import com.devrex.network.base.ApiClient
import com.devrex.network.base.ApiError
import com.devrex.network.base.Resource
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse


class SafeRestCallBack {


    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    @PublishedApi
    internal suspend inline fun <reified T> callPostApi(
        baseUrl: String,
        endPoint: String,
        headers: HashMap<String, String?>,
        postParams: String,
        queryMap: HashMap<String, String>?,
        timeOut: Long,
    ): Resource<T> {
        val typedBody =
            postParams.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return safeRestCallBack {
            getRestApiInterface(
                baseUrl, timeOut
            )?.callPostApi(
                baseUrl + endPoint, headers, typedBody, queryMap
            )
        }
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    @PublishedApi
    internal suspend inline fun <reified T> callGetApi(
        baseUrl: String,
        endPoint: String,
        queryMap: HashMap<String, String>?,
        timeOut: Long,
    ): Resource<T> {
        return safeRestCallBack {
            getRestApiInterface(
                baseUrl,
                timeOut
            )?.callGetApi(
                baseUrl + endPoint, queryMap
            )
        }
    }

    internal suspend inline fun <reified T> safeRestCallBack(
        call: () -> Call<ResponseBody>?,
    ): Resource<T> {
        val response = call.invoke()?.awaitResponse()
        return parseSafeRestCallBack(response)
    }

     inline fun <reified T> parseSafeRestCallBack(response: Response<ResponseBody>?): Resource<T> {
        try {
            if (response?.isSuccessful == true) {
                return if (response.body() != null) {
                    try {
                        parseResponse(response.body()!!.string())
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Resource.Error(ApiError(message = e.message))
                    }
                } else {
                    Resource.Error(ApiError(message = response.message()))

                }
            } else if (response?.errorBody() != null) {
                return parseErrorResponse(response.errorBody()!!.string())
            } else {
                return Resource.Error(ApiError(message = response?.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(ApiError(message = e.message))
        }

    }

    inline fun <reified T> parseResponse(response: String?): Resource<T> {
        return when (T::class.java) {
            String::class.java -> Resource.Success(data = response as? T)
            JSONObject::class.java -> Resource.Success(data = JSONObject(response.toString()) as? T)
            JSONArray::class.java -> Resource.Success(data = JSONArray(response.toString()) as? T)
            JsonObject::class.java -> Resource.Success(data = JsonObject().getAsJsonObject(response.toString()) as? T)
            JsonArray::class.java -> Resource.Success(data = JsonObject().getAsJsonArray(response.toString()) as? T)

            else -> Resource.Success(Gson().fromJson(response, T::class.java))
        }
    }

    inline fun <reified T> parseErrorResponse(response: String?): Resource<T> {
        return try {
            Resource.Error(Gson().fromJson(response, ApiError::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(ApiError(message = e.message))
        }
    }


    internal fun getRestApiInterface(
        baseUrl: String,
        timeOut: Long,
    ): RestApiInterface? {
        return ApiClient().getRetrofitApiClient(
            baseUrl, timeOut
        )?.create(RestApiInterface::class.java)
    }
}