package com.devrex.sharedtest

import com.devrex.network.base.Resource
import com.devrex.network.retrofit.RestApiInterface
import com.devrex.network.retrofit.SafeRestCallBack
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.awaitResponse

@RunWith(JUnit4::class)
open class CreateApiService : SafeRestTestCallBack<RestApiInterface>() {


    var service: RestApiInterface? = null

    @Before
    override fun setup() {
        super.setup()
        service = createService(RestApiInterface::class.java)
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    suspend inline fun <reified T : Any> callTestApi(
        endPoint: String,
        headers: HashMap<String, String?>?,
        request: String,
        queryMap: HashMap<String, String>,
        responseFileName: String,
        responseCode: Int
    ): Resource<T> {
        enqueueResponse(responseFileName, responseCode)
        var safeRestCallBackResponse: Response<ResponseBody>? = null
        runTest {
            safeRestCallBackResponse = service?.callPostApi(
                endPoint,
                headers,
                request.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()),
                queryMap
            )?.awaitResponse()
        }
        assertRequestPath(endPoint)
        return SafeRestCallBack().parseSafeRestCallBack(safeRestCallBackResponse)
    }

}