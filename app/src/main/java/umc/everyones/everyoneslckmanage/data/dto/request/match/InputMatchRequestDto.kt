package umc.everyones.everyoneslckmanage.data.dto.request.match

data class InputMatchRequestDto (
    val team1Id: Int,
    val team2Id: Int,
    val seasonId: Long,
    val matchNumber: Long,
    val matchResult: String,
    val matchDate: String
)