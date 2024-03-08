package com.devrex.networkapplication.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResultModel(
    var adult: Boolean? = null,
    var backdropPath: String? = null,
    val genreIds: List<Int?>? = null,
    var id: Int? = null,
    var originalLanguage: String? = null,
    var originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    var posterPath: String? = null,
    var releaseDate: String? = null,
    var title: String? = null,
    val video: Boolean? = null,
    var voteAverage: Float? = null,
    var voteCount: Int? = null,
) : Parcelable