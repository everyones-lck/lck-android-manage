package umc.everyones.everyoneslckmanage.domain.model.request

import umc.everyones.everyoneslckmanage.data.dto.request.login.LoginRequestDto
import java.io.Serializable

data class LoginRequestModel(
    val kakaoUserId: String
): Serializable{
    fun toLoginRequestDto()= LoginRequestDto(kakaoUserId)
}
