package com.devrex.networkapplication.domain.usecases

import com.devrex.network.base.ApiError
import com.devrex.network.base.Resource
import com.devrex.networkapplication.data.dto.MovieDetailsResponseDto
import com.devrex.networkapplication.data.repositoryimpl.RemoteRepositoryImpl
import com.devrex.networkapplication.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MovieDetailsUsecase(
    private val repository: RemoteRepository = RemoteRepositoryImpl(),
) {

    suspend operator fun invoke(movieId: String): Flow<Resource<MovieDetailsResponseDto>> = flow {
        emit(Resource.Loading(true))
        val response = repository.movieDetails(movieId)
        emit(Resource.Loading(false))
        emit(response)
    }.catch {
        emit(Resource.Loading(false))
        emit(Resource.Error(ApiError(message = it.message)))
    }
}