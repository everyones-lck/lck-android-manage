package umc.everyones.everyoneslckmanage.data.dto.response.match

import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel

data class LckMatchDetailsResponseDto(
    val matchByDateList: List<LckMatchByDateDto>
) {
    data class LckMatchByDateDto(
        val matchDate: String,
        val matchDetailList: List<LckMatchDetailsElementDto>,
        val matchDetailSize: Int
    ) {
        fun toAboutLckMatchByDateModel() =
            LckMatchDetailsModel.LckMatchByDateModel(
                matchDate = matchDate,
                matchDetailList = matchDetailList.map { it.toAboutLckMatchDetailsElementModel() },
                matchDetailSize = matchDetailSize
            )
    }

    data class LckMatchDetailsElementDto(
        val team1: TeamElementDto,
        val team2: TeamElementDto,
        val matchFinished: Boolean,
        val season: String,
        val matchNumber: Int,
        val matchTime: String,
        val matchDate: String
    ) {
        data class TeamElementDto(
            val teamName: String,
            val teamLogoUrl: String,
            val winner: Boolean
        ) {
            fun toTeamElementModel() =
                LckMatchDetailsModel.LckMatchDetailsElementModel.TeamElementModel(
                    teamName, teamLogoUrl, winner
                )
        }

        fun toAboutLckMatchDetailsElementModel() =
            LckMatchDetailsModel.LckMatchDetailsElementModel(
                team1.toTeamElementModel(),
                team2.toTeamElementModel(),
                matchFinished,
                season,
                matchNumber,
                matchTime,
                matchDate
            )
    }

    fun toAboutLckMatchDetailsModel() =
        LckMatchDetailsModel(
            matchByDateList = matchByDateList.map { it.toAboutLckMatchByDateModel() }
        )
}