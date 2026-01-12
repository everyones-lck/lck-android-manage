package umc.everyones.everyoneslckmanage.data.dto.request.community

data class PageableRequestDto(
    val page: Int,
    val size: Int,
    val sort: List<String>
)
