package com.devrex.networkapplication.presentation.detail_page

import androidx.lifecycle.ViewModel
import com.devrex.network.base.Resource
import com.devrex.networkapplication.data.mapper.copyTo
import com.devrex.networkapplication.domain.usecases.MovieDetailsUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class DetailsPageViewModel(
    val movieDetailsUsecase: MovieDetailsUsecase = MovieDetailsUsecase(),
    val movieId: String? = "",
) : ViewModel() {

    val viewState = MutableStateFlow(DetailPageViewState())

    suspend fun callDetailsApi() {
        movieDetailsUsecase(movieId.orEmpty()).collect {
            when (it) {
                is Resource.Loading -> {
                    viewState.update { state ->
                        state.copy(
                            showProgress = it.isLoading
                        )
                    }
                }

                is Resource.Success -> {
                    val movieDetailsModel = it.data?.copyTo()
                    viewState.update { state ->
                        state.copy(
                            movieDetailsModel = movieDetailsModel
                        )
                    }

                }

                is Resource.Error -> {

                }
            }
        }
    }
}