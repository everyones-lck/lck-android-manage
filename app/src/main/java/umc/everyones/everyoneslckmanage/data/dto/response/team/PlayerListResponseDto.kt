package umc.everyones.everyoneslckmanage.data.dto.response.team

import umc.everyones.everyoneslckmanage.domain.model.response.team.PlayerListModel

data class PlayerListResponseDto(
    val players: List<PlayerDto>,
    val numberOfPlayerDetail: Int,
    val seasonName: String
) {
    data class PlayerDto(
        val playerId: Int,
        val playerName: String,
        val playerRole: String,
        val playerPosition: String,
        val profileImageUrl: String
    ) {
        fun toPlayerListModel(): PlayerListModel.PlayerModel =
            PlayerListModel.PlayerModel(
                playerId = playerId,
                playerName = playerName,
                playerRole = playerRole,
                playerPosition = playerPosition,
                imageUrl = profileImageUrl
            )
    }

    fun toPlayerListModel(): PlayerListModel =
        PlayerListModel(
            players = players.map { it.toPlayerListModel() },
            numberOfPlayerDetail = numberOfPlayerDetail,
            seasonName = seasonName
        )
}
