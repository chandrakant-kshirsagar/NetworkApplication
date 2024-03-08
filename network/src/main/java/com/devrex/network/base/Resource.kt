package com.devrex.network.base

sealed class Resource<T>(
    val data: T? = null,
    val apiError: ApiError? = null,
) {

    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(apiError: ApiError? = null) : Resource<T>(null, apiError)
    class Loading<T>(val isLoading: Boolean = false) : Resource<T>()
}