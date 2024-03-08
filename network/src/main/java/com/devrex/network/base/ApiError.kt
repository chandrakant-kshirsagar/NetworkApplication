package com.devrex.network.base


import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("status_message")
    val message: String? = null,
    @SerializedName("success")
    val success: Boolean? = null,
)