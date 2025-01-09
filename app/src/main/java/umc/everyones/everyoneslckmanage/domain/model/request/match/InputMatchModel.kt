package umc.everyones.everyoneslckmanage.domain.model.request.match

import umc.everyones.everyoneslckmanage.data.dto.request.match.InputMatchRequestDto
import java.io.Serializable

data class InputMatchModel(
    val team1Id: Int,
    val team2Id: Int,
    val seasonId: Long,
    val matchNumber: Long,
    val matchResult: String = "NOT_FINISHED",
    val matchDate: String
): Serializable {
    fun toInputMatchRequestDto() =
        InputMatchRequestDto(
            team1Id,
            team2Id,
            seasonId,
            matchNumber,
            matchResult,
            matchDate
        )
}
