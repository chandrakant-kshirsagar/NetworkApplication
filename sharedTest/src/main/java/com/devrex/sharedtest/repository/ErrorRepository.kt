package com.devrex.sharedtest.repository

import com.devrex.network.base.Resource
import com.devrex.sharedtest.CreateApiService
import org.json.JSONObject

interface ErrorRepository {

    val createApiService: CreateApiService
    suspend fun getERROR_301_response(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
    ): Resource<JSONObject> = createApiService.callTestApi(
        endPoint,headers, request,queryMap, responseFileName, HttpStatusCodes.ERROR_301.type
    )


    suspend fun getERROR_302_response(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
    ): Resource<JSONObject> = createApiService.callTestApi(
        endPoint,headers, request,queryMap, responseFileName, HttpStatusCodes.ERROR_302.type
    )

    suspend fun getERROR_304_response(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
    ): Resource<JSONObject> = createApiService.callTestApi(
        endPoint,headers, request,queryMap, responseFileName, HttpStatusCodes.ERROR_304.type
    )

    suspend fun getERROR_400_response(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
    ): Resource<JSONObject> = createApiService.callTestApi(
        endPoint,headers, request,queryMap, responseFileName, HttpStatusCodes.ERROR_400.type
    )

    suspend fun getERROR_401_response(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
    ): Resource<JSONObject> = createApiService.callTestApi(
        endPoint,headers, request,queryMap, responseFileName, HttpStatusCodes.ERROR_401.type
    )

    suspend fun getERROR_403_response(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
    ): Resource<JSONObject> = createApiService.callTestApi(
        endPoint,headers, request,queryMap, responseFileName, HttpStatusCodes.ERROR_403.type
    )

    suspend fun getERROR_404_response(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
    ): Resource<JSONObject> = createApiService.callTestApi(
        endPoint,headers, request,queryMap, responseFileName, HttpStatusCodes.ERROR_404.type
    )

    suspend fun getERROR_500_response(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
    ): Resource<JSONObject> = createApiService.callTestApi(
        endPoint,headers, request,queryMap, responseFileName, HttpStatusCodes.ERROR_500.type
    )

    suspend fun getERROR_501_response(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
    ): Resource<JSONObject> = createApiService.callTestApi(
        endPoint,headers, request,queryMap, responseFileName, HttpStatusCodes.ERROR_501.type
    )
}