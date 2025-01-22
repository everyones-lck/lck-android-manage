package umc.everyones.everyoneslckmanage.domain.model.response.team

data class PlayerListModel(
    val players: List<PlayerModel>,
    val numberOfPlayerDetail: Int,
    val seasonName: String
) {
    data class PlayerModel(
        val playerId: Int,
        val playerName: String,
        val playerRole: String,
        val playerPosition: String,
        val imageUrl: String? = null
    )
}