package com.devrex.networkapplication.data.mapper

import com.devrex.networkapplication.data.dto.MovieDetailsResponseDto
import com.devrex.networkapplication.data.dto.MovieResult
import com.devrex.networkapplication.domain.model.MovieDetailsModel
import com.devrex.networkapplication.domain.model.MovieResultModel

fun MovieResult.copyTo(): MovieResultModel {
    val movieResultModel = MovieResultModel()
    movieResultModel.id = this.id
    movieResultModel.title = this.title
    movieResultModel.originalTitle = this.originalTitle
    movieResultModel.adult = this.adult
    movieResultModel.originalLanguage = this.originalLanguage
    movieResultModel.backdropPath = this.backdropPath
    movieResultModel.voteCount = this.voteCount
    movieResultModel.voteAverage = this.voteAverage?.toFloat()?.div(10)
    movieResultModel.releaseDate = this.releaseDate
    movieResultModel.posterPath = this.posterPath
    return movieResultModel
}

fun MovieDetailsResponseDto.copyTo(): MovieDetailsModel {
    return MovieDetailsModel(
        this.backdropPath,
        this.posterPath,
        this.title,
        this.releaseDate,
        this.overview,
        this.voteAverage?.toFloat()?.div(10),
        this.genres,
        this.productionCompanies
    )
}