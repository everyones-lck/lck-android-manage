package umc.everyones.everyoneslckmanage.domain.model.response.match

data class SetResultInfoResponseModel(
    val setsInformation: List<SetsInformationModel>
){
    data class SetsInformationModel(
        val setIndex: Int,
        val winnerTeam: String,
        val loserTeam: String
    )
}
