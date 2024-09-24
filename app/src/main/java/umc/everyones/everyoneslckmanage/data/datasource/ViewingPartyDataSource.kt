package umc.everyones.everyoneslckmanage.data.datasource

import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.response.party.ReadViewingPartyResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.party.ViewingPartyListResponseDto


interface ViewingPartyDataSource {
    suspend fun fetchViewingPartyList(page: Int, size: Int): BaseResponse<ViewingPartyListResponseDto>

    suspend fun fetchViewingParty(viewingPartyId: Long): BaseResponse<ReadViewingPartyResponseDto>

}