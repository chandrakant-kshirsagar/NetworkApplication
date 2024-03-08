package com.devrex.networkapplication.presentation.movie

import com.devrex.networkapplication.domain.model.MovieResultModel

data class MovieListViewState(
    val latestMovieList: ArrayList<MovieResultModel>? = null,
    val popularMovieList: ArrayList<MovieResultModel>? = null,
    val showProgress: Boolean = false,
)