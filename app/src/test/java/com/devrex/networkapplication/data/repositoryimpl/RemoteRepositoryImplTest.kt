package com.devrex.networkapplication.data.repositoryimpl

import com.devrex.networkapplication.domain.repository.FakeRemoteRepository
import com.devrex.sharedtest.CreateApiService
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import kotlinx.coroutines.test.runTest


class RemoteRepositoryImplTest : CreateApiService() {

    private lateinit var fakeRemoteRepository: FakeRemoteRepository

    @Before
    override fun setup() {
        super.setup()
        fakeRemoteRepository = FakeRemoteRepositoryImpl(this)
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @org.junit.Test
    fun latestMovie() = runTest {
        val latestMovie = fakeRemoteRepository.latestMovie()
        Truth.assertThat(latestMovie).isNotNull()
    }

    @org.junit.Test
    fun popularMovie() = runTest {
        val popularMovie = fakeRemoteRepository.popularMovie()
        Truth.assertThat(popularMovie).isNotNull()
    }

    @org.junit.Test
    fun movieDetails()= runTest {
        val movieDetails = fakeRemoteRepository.movieDetails("3")
        Truth.assertThat(movieDetails).isNotNull()
    }
}