package umc.everyones.everyoneslckmanage.domain.model.response.team

import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerUpdateRequestDto
import java.io.Serializable

data class PlayerUpdateModel(
    val playerId: Int,
    val name: String,
    val realName: String,
    val position: String,
    val birthday: String
): Serializable {
    fun toPlayerUpdateRequestDto(): PlayerUpdateRequestDto =
        PlayerUpdateRequestDto(
            playerId = this.playerId,
            name = this.name,
            realName = this.realName,
            position = this.position,
            birthday = this.birthday
        )
}