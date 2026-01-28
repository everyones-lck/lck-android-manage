package umc.everyones.everyoneslckmanage.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.login.LoginRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.login.LoginResponseDto

interface LoginService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): BaseResponse<LoginResponseDto>
}