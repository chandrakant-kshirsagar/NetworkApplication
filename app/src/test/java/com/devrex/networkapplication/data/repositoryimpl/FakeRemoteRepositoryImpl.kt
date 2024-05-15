package com.devrex.networkapplication.data.repositoryimpl

import com.devrex.network.base.Resource
import com.devrex.networkapplication.data.ApiUrls
import com.devrex.networkapplication.data.dto.MovieDetailsResponseDto
import com.devrex.networkapplication.data.dto.MovieResponseDto
import com.devrex.networkapplication.domain.repository.FakeRemoteRepository
import com.devrex.sharedtest.CreateApiService
import com.devrex.sharedtest.repository.HttpStatusCodes

class FakeRemoteRepositoryImpl(override val createApiService: CreateApiService) :
    FakeRemoteRepository {
    override suspend fun latestMovie(): Resource<MovieResponseDto> =
        createApiService.callTestApi(
            "/popular",
            hashMapOf(),
            "",
            hashMapOf(),
            "/responses/upcoming_response.json",
            HttpStatusCodes.ERROR_301.type
        )

    override suspend fun popularMovie(): Resource<MovieResponseDto> = createApiService.callTestApi(
        "/popular",
        hashMapOf(),
        "",
        hashMapOf(),
        "/responses/upcoming_response.json",
        HttpStatusCodes.ERROR_200.type
    )

    override suspend fun movieDetails(movieId: String): Resource<MovieDetailsResponseDto> =
        createApiService.callTestApi(
            "/popular",
            hashMapOf(),
            movieId,
            hashMapOf(),
            "/responses/upcoming_response.json",
            HttpStatusCodes.ERROR_200.type
        )
}