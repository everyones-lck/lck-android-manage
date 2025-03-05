package umc.everyones.everyoneslckmanage.data.dto.request.team

import umc.everyones.everyoneslckmanage.domain.model.response.team.PlayerUpdateModel

data class PlayerUpdateRequestDto(
    val playerId: Int,
    val name: String,
    val realName: String,
    val position: String,
    val birthday: String
) {
    fun toPlayerUpdateModel(): PlayerUpdateModel =
        PlayerUpdateModel(
        playerId = playerId,
        name = name,
        realName = realName,
        position = position,
        birthday = birthday
    )
}