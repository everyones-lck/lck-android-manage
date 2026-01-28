package umc.everyones.everyoneslckmanage.data.datasource

import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.login.LoginRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.login.LoginResponseDto

interface LoginDataSource {
    suspend fun login(requestDto: LoginRequestDto): BaseResponse<LoginResponseDto>
}