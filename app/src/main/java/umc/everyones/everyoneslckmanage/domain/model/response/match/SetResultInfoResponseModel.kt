package umc.everyones.everyoneslckmanage.domain.model.response.match

data class SetResultInfoResponseModel(
    val setInformation: List<SetInformationModel>
){
    data class SetInformationModel(
        val setIndex: Int,
        val winnerTeam: String,
        val loserTeam: String
    )
}
