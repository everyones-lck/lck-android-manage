package umc.everyones.everyoneslckmanage.data.dto.response.match

import umc.everyones.everyoneslckmanage.domain.model.response.match.SetResultInfoResponseModel

data class SetResultInfoResponseDto(
    val setsInformation: List<SetsInformationDto>
){
    data class SetsInformationDto(
        val setIndex: Int,
        val winnerTeam: String,
        val loserTeam: String
    ) {
        fun toSetsInformationModel() =
            SetResultInfoResponseModel.SetsInformationModel(setIndex, winnerTeam, loserTeam)
    }
    fun toSetResultInfoResponseModel() =
        SetResultInfoResponseModel(setsInformation?.map { it.toSetsInformationModel() } ?: emptyList())
}
