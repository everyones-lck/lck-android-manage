package umc.everyones.everyoneslckmanage.domain.model.request.community

import umc.everyones.everyoneslckmanage.data.dto.request.community.PageableRequestDto
import java.io.Serializable

data class PageableRequestModel(
    val page: Int,
    val size: Int,
    val sort: List<String>
): Serializable {
    fun toPageableRequestDto() =
        PageableRequestDto(page, size, sort)
}
