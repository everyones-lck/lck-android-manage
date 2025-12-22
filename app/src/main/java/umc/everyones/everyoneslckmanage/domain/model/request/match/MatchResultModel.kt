package umc.everyones.everyoneslckmanage.domain.model.request.match

import umc.everyones.everyoneslckmanage.data.dto.request.match.MatchResultRequestDto
import java.io.Serializable

data class MatchResultModel(
    val matchId: Long,
    val winnerTeamId: Int
): Serializable {
    fun toMatchResultRequestDto() =
        MatchResultRequestDto(
            matchId, winnerTeamId
        )
}
