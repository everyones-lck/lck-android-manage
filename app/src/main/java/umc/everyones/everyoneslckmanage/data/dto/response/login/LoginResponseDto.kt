package umc.everyones.everyoneslckmanage.data.dto.response.login

import umc.everyones.everyoneslckmanage.domain.model.response.login.LoginResponseModel

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTime: String,
    val refreshTokenExpirationTime: String,
    val nickName: String
){
    fun toLoginResponseModel() = LoginResponseModel(accessToken, refreshToken, accessTokenExpirationTime, refreshTokenExpirationTime, nickName)
}
