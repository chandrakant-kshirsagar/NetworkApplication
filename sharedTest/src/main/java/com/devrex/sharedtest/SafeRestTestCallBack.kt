package com.devrex.sharedtest

import com.devrex.network.base.ToStringConverterFactory
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
abstract class SafeRestTestCallBack<T> : UseCaseTestWatcher() {

    private lateinit var mockWebServer: MockWebServer

    @Throws(IOException::class)
    @Before
    open fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Throws(IOException::class)
    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String, responseCode: Int = 200) {
        enqueueResponse(fileName, emptyMap(), responseCode)
    }

    @Throws(IOException::class)
    private fun enqueueResponse(
        fileName: String,
        headers: Map<String, String>,
        responseCode: Int = 200
    ) {
        val response = Helper.readFileResource(fileName)
        val mockResponse = MockResponse()
            .setBody(response).setResponseCode(responseCode)

        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse)
    }

    fun createService(clazz: Class<T>): T {
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ToStringConverterFactory())
            .build()
            .create(clazz)
    }

    fun getMockedServer(): MockWebServer {
        return mockWebServer
    }

    fun assertRequestPath(path: String) {
        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`(path))
    }
}
