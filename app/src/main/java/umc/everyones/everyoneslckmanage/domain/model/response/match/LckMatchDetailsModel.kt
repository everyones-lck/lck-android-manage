package umc.everyones.everyoneslckmanage.domain.model.response.match

data class LckMatchDetailsModel(
    val matchByDateList: List<LckMatchByDateModel>
) {
    data class LckMatchByDateModel(
        val matchDate: String,
        val matchDetailList: List<LckMatchDetailsElementModel>,
        val matchDetailSize: Int
    )

    data class LckMatchDetailsElementModel(
        val team1: TeamElementModel,
        val team2: TeamElementModel,
        val matchFinished: Boolean,
        val season: String,
        val matchNumber: Int,
        val matchTime: String,
        val matchDate: String
    ) {
        data class TeamElementModel(
            val teamName: String,
            val teamLogoUrl: String,
            val winner: Boolean
        )
    }
}