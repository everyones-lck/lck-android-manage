package umc.everyones.everyoneslckmanage.data.dto.request.match

data class SetResultRequestDto(
    val matchId: Long,
    val setIndex: Int,
    val winnerTeamId: Int,
    val loseTeamId: Int
)
