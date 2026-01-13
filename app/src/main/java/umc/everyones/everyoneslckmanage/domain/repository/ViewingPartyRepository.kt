package umc.everyones.everyoneslckmanage.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import umc.everyones.everyoneslckmanage.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyListModel
import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyWithReportListModel

interface ViewingPartyRepository {
    suspend fun getViewingPartyWithReportList(page: Int, size: Int): Result<ViewingPartyWithReportListModel>
    suspend fun fetchViewingParty(viewingPartyId: Long): Result<ReadViewingPartyModel>
    fun fetchViewingPartyListPagingSource(): Flow<PagingData< ViewingPartyWithReportListModel.ViewingPartyWithReportListElementModel>>
    suspend fun deleteViewingParty(viewingPartyId: Long): Result<Unit>
}