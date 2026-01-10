package umc.everyones.everyoneslckmanage.domain.model.request.match

import umc.everyones.everyoneslckmanage.data.dto.request.match.CloseMatchRequestDto
import java.io.Serializable

data class CloseMatchModel(
    val matchId: Long
): Serializable {
    fun toCloseMatchRequestDto() =
        CloseMatchRequestDto(matchId)
}
