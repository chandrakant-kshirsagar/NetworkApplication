package com.devrex.networkapplication.data.repositoryimpl

import com.devrex.network.base.Resource
import com.devrex.network.retrofit.NetworkCall
import com.devrex.networkapplication.data.ApiUrls
import com.devrex.networkapplication.data.dto.MovieDetailsResponseDto
import com.devrex.networkapplication.data.dto.MovieResponseDto
import com.devrex.networkapplication.domain.repository.RemoteRepository

class RemoteRepositoryImpl : RemoteRepository {
    override suspend fun latestMovie(): Resource<MovieResponseDto> =
        NetworkCall().callGet(
            ApiUrls.BASE_URL,
            "upcoming",
            hashMapOf("api_key" to ApiUrls.APP_KEY)
        )

    override suspend fun popularMovie(): Resource<MovieResponseDto> = NetworkCall().callGet(
        ApiUrls.BASE_URL, "popular", hashMapOf("api_key" to ApiUrls.APP_KEY)
    )

    override suspend fun movieDetails(movieId: String): Resource<MovieDetailsResponseDto> =
        NetworkCall().callGet(
            ApiUrls.BASE_URL, movieId,hashMapOf("api_key" to ApiUrls.APP_KEY)
        )
}