package umc.everyones.everyoneslckmanage.domain.model.request.match

import umc.everyones.everyoneslckmanage.data.dto.request.match.SetResultRequestDto
import java.io.Serializable

data class SetResultModel(
    val matchId: Long,
    val setIndex: Int,
    val winnerTeamId: Int,
    val loseTeamId: Int
): Serializable {
    fun toSetResultRequestDto() =
        SetResultRequestDto(
            matchId, setIndex, winnerTeamId, loseTeamId
        )
}
