package umc.everyones.everyoneslckmanage.data.dto.response.community

import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityReportCauseResponseModel

data class CommunityReportCauseResponseDto(
    val postReportCauseList: List<String>,
    val size: Int
){
    fun toCommunityReportCauseResponseModel() =
        CommunityReportCauseResponseModel(postReportCauseList, size)
}