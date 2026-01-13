package umc.everyones.everyoneslckmanage.data.datasource

import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.response.party.ReadViewingPartyResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.party.ViewingPartyListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.party.ViewingPartyWithReportListResponseDto


interface ViewingPartyDataSource {
    suspend fun getViewingPartyWithReportList(page: Int, size: Int): BaseResponse<ViewingPartyWithReportListResponseDto>
    suspend fun fetchViewingParty(viewingPartyId: Long): BaseResponse<ReadViewingPartyResponseDto>
    suspend fun deleteViewingParty(viewingPartyId: Long): BaseResponse<Unit>
}