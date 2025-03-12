package umc.everyones.everyoneslckmanage.data.dto.response.match

import umc.everyones.everyoneslckmanage.domain.model.response.match.SetResultInfoResponseModel

data class SetResultInfoResponseDto(
    val setInformation: List<SetInformationDto>
){
    data class SetInformationDto(
        val setIndex: Int,
        val winnerTeam: String,
        val loserTeam: String
    ) {
        fun toSetInformationModel() =
            SetResultInfoResponseModel.SetInformationModel(setIndex, winnerTeam, loserTeam)
    }
    fun toSetResultInfoResponseModel() =
        SetResultInfoResponseModel(setInformation.map { it.toSetInformationModel() })
}
