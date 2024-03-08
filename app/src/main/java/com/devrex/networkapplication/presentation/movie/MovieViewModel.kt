package com.devrex.networkapplication.presentation.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrex.network.base.Resource
import com.devrex.networkapplication.data.mapper.copyTo
import com.devrex.networkapplication.domain.model.MovieResultModel
import com.devrex.networkapplication.domain.usecases.LatestMovieUsecase
import com.devrex.networkapplication.domain.usecases.PopularMovieUsecase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MovieViewModel(
    val latestMovieUsecase: LatestMovieUsecase = LatestMovieUsecase(),
    val popularMovieUsecase: PopularMovieUsecase = PopularMovieUsecase(),
) : ViewModel() {

    private val latestMovieResultModelList = arrayListOf<MovieResultModel>()
    private val popularMovieResultModelList = arrayListOf<MovieResultModel>()
    val viewState = MutableStateFlow(MovieListViewState())


    suspend fun callApis() {
        viewState.update { viewstate ->
            viewstate.copy(
                showProgress = true
            )
        }
        val latestJob = viewModelScope.async { callLatestMovieApi() }
        val popularJob = viewModelScope.async { callPopularMovieApi() }

        latestJob.await()
        popularJob.await()

        viewState.update { viewstate ->
            viewstate.copy(
                showProgress = false
            )
        }
    }

    private suspend fun callLatestMovieApi() {
        latestMovieUsecase().collect { it ->
            when (it) {
                is Resource.Loading -> {
                    viewState.update { viewstate ->
                        viewstate.copy(
                            showProgress = it.isLoading
                        )
                    }
                }

                is Resource.Success -> {
                    Log.d("MovieApi", "callLatestMovieApi: ${it.data}")
                    val movieResponse = it.data
                    movieResponse?.let { response ->
                        response.movieResults?.forEach { result ->
                            val movieResultModel = result?.copyTo()
                            movieResultModel?.let {
                                    it1,
                                ->
                                latestMovieResultModelList.add(it1)
                            }
                        }
                        viewState.update {
                            it.copy(
                                latestMovieList = latestMovieResultModelList
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    Log.d("MovieApi", "callLatestMovieApi: ${it.apiError?.message}")
                }
            }
        }
    }


    private suspend fun callPopularMovieApi() {
        popularMovieUsecase().collect { it ->
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    Log.d("MovieApi", "callLatestMovieApi: ${it.data}")
                    val movieResponse = it.data
                    movieResponse?.let { response ->
                        response.movieResults?.forEach { result ->
                            val movieResultModel = result?.copyTo()
                            movieResultModel?.let {
                                    it1,
                                ->
                                popularMovieResultModelList.add(it1)
                            }
                        }
                        viewState.update {
                            it.copy(
                                popularMovieList = popularMovieResultModelList
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    Log.d("MovieApi", "callLatestMovieApi: ${it.apiError?.message}")
                }
            }
        }
    }

}