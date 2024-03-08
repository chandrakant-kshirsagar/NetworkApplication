package com.devrex.networkapplication.domain.repository

import com.devrex.network.base.Resource
import com.devrex.networkapplication.data.dto.MovieDetailsResponseDto
import com.devrex.networkapplication.data.dto.MovieResponseDto
import com.devrex.networkapplication.data.dto.MovieResult

interface RemoteRepository {

    suspend fun latestMovie(): Resource<MovieResponseDto>
    suspend fun popularMovie(): Resource<MovieResponseDto>
    suspend fun movieDetails(movieId:String): Resource<MovieDetailsResponseDto>
}