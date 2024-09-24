package umc.everyones.everyoneslckmanage.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import umc.everyones.everyoneslckmanage.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyListModel

interface ViewingPartyRepository {
    suspend fun fetchViewingPartyList(page: Int, size: Int): Result<ViewingPartyListModel>

    suspend fun fetchViewingParty(viewingPartyId: Long): Result<ReadViewingPartyModel>
    fun fetchViewingPartyListPagingSource(): Flow<PagingData<ViewingPartyListModel.ViewingPartyElementModel>>
}