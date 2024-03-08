package com.devrex.networkapplication.domain.usecases

import com.devrex.network.base.ApiError
import com.devrex.network.base.Resource
import com.devrex.networkapplication.data.dto.MovieResponseDto
import com.devrex.networkapplication.data.repositoryimpl.RemoteRepositoryImpl
import com.devrex.networkapplication.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PopularMovieUsecase(
    private val repository: RemoteRepository = RemoteRepositoryImpl(),
) {

    suspend operator fun invoke(): Flow<Resource<MovieResponseDto>> = flow {
        emit(Resource.Loading(true))
        val response = repository.popularMovie()
        emit(Resource.Loading(false))
        emit(response)
    }.catch {
        emit(Resource.Loading(false))
        emit(Resource.Error(ApiError(message = it.message)))
    }
}