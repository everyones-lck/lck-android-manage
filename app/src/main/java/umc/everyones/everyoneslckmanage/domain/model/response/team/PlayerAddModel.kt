package umc.everyones.everyoneslckmanage.domain.model.response.team

data class PlayerAddModel(
    val profileImage: String,
    val request: PlayerAddDetailModel
) {
    data class PlayerAddDetailModel(
        val teamId: Int,
        val name: String,
        val realName: String,
        val position: String,
        val season: String,
        val role: String,
        val isCaptain: Boolean,
        val birth: String
    )
}