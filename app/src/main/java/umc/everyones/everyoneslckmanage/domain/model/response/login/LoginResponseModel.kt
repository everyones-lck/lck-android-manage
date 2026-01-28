package umc.everyones.everyoneslckmanage.domain.model.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTime: String,
    val refreshTokenExpirationTime: String,
    val nickName: String
)
