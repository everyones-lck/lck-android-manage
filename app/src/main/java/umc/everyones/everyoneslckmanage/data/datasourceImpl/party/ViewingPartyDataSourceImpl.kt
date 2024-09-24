package umc.everyones.everyoneslckmanage.data.datasourceImpl.party


import umc.everyones.everyoneslckmanage.data.datasource.ViewingPartyDataSource
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.response.party.ReadViewingPartyResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.party.ViewingPartyListResponseDto
import umc.everyones.everyoneslckmanage.data.service.ViewingPartyService
import javax.inject.Inject

class ViewingPartyDataSourceImpl @Inject constructor(
    private val viewingPartyService: ViewingPartyService
): ViewingPartyDataSource {
    override suspend fun fetchViewingPartyList(
        page: Int,
        size: Int
    ): BaseResponse<ViewingPartyListResponseDto> =
        viewingPartyService.fetchViewingPartyList(page, size)

    override suspend fun fetchViewingParty(viewingPartyId: Long): BaseResponse<ReadViewingPartyResponseDto> =
        viewingPartyService.fetchViewingParty(viewingPartyId)

}