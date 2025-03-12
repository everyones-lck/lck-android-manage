package umc.everyones.everyoneslckmanage.domain.model.match

data class SelectedMatch(
    val matchNumber: Long,
    val seasonTitle: String,
    val matchDate: String,
    val matchTime: String,
    val team1Name: String,
    val team2Name: String
)
