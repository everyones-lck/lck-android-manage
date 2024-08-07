package umc.everyones.everyoneslckmanage.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.TestRequest
import umc.everyones.everyoneslckmanage.data.dto.response.TestResponse

interface TestService {
    // api 명세서의 api 주소 기입
    @GET("주소")
    suspend fun fetchTest(@Body request: TestRequest): Response<BaseResponse<TestResponse>>
}