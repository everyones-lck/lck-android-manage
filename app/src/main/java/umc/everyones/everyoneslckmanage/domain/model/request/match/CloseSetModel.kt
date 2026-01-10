package umc.everyones.everyoneslckmanage.domain.model.request.match

import umc.everyones.everyoneslckmanage.data.dto.request.match.CloseSetRequestDto
import java.io.Serializable

data class CloseSetModel(
    val setId: Long
): Serializable {
    fun toCloseSetRequestDto() =
        CloseSetRequestDto(setId)
}
