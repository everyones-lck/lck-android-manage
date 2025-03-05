package umc.everyones.everyoneslckmanage.domain.model.response.team

data class PlayerUpdateModel(
    val playerId: Int,
    val name: String,
    val realName: String,
    val position: String,
    val birthday: String
)