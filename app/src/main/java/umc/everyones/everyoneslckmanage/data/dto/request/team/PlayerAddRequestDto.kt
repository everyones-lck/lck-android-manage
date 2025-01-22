package umc.everyones.everyoneslckmanage.data.dto.request.team

import umc.everyones.everyoneslckmanage.domain.model.response.team.PlayerAddModel

data class PlayerAddRequestDto(
    val profileImage: String,
    val request: PlayerAddDetailDto
) {
    data class PlayerAddDetailDto(
        val teamId: Int,
        val name: String,
        val realName: String,
        val position: String,
        val season: String,
        val role: String,
        val isCaptain: Boolean,
        val birth: String
    ) {
        fun toPlayerAddModel(): PlayerAddModel.PlayerAddDetailModel =
            PlayerAddModel.PlayerAddDetailModel(
                teamId = teamId,
                name = name,
                realName = realName,
                position = position,
                season = season,
                role = role,
                isCaptain = isCaptain,
                birth = birth
            )
    }

    fun toPlayerAddModel(): PlayerAddModel =
        PlayerAddModel(
            profileImage = profileImage,
            request = request.toPlayerAddModel()
        )
}
