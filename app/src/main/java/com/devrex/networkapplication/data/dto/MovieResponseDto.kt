package com.devrex.networkapplication.data.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponseDto(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val movieResults: List<MovieResult?>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
) : Parcelable