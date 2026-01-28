package umc.everyones.everyoneslckmanage.data.datasourceImpl.login

import umc.everyones.everyoneslckmanage.data.datasource.LoginDataSource
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.login.LoginRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.login.LoginResponseDto
import umc.everyones.everyoneslckmanage.data.service.LoginService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService
): LoginDataSource{
    override suspend fun login(requestDto: LoginRequestDto): BaseResponse<LoginResponseDto> =
        loginService.login(requestDto)
}