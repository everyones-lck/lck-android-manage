package umc.everyones.everyoneslckmanage.data.repositoryImpl

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import umc.everyones.everyoneslckmanage.data.datasource.ViewingPartyDataSource
import umc.everyones.everyoneslckmanage.data.datasourceImpl.party.ViewingPartyListPagingSource
import umc.everyones.everyoneslckmanage.data.service.ViewingPartyService
import umc.everyones.everyoneslckmanage.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyListModel
import umc.everyones.everyoneslckmanage.domain.repository.ViewingPartyRepository
import javax.inject.Inject

class ViewingPartyRepositoryImpl @Inject constructor(
    private val viewingPartyDataSource: ViewingPartyDataSource,
    private val viewingPartyService: ViewingPartyService,
    private val spf: SharedPreferences
) : ViewingPartyRepository {
    override suspend fun fetchViewingPartyList(
        page: Int,
        size: Int
    ): Result<ViewingPartyListModel> = runCatching {
        viewingPartyDataSource.fetchViewingPartyList(
            page,
            size
        ).data.toViewingPartyListModel()
    }

    override suspend fun fetchViewingParty(viewingPartyId: Long): Result<ReadViewingPartyModel> =
        runCatching {
            viewingPartyDataSource.fetchViewingParty(viewingPartyId).data.toReadViewingPartyModel()
        }

    override fun fetchViewingPartyListPagingSource(): Flow<PagingData<ViewingPartyListModel.ViewingPartyElementModel>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { ViewingPartyListPagingSource(viewingPartyService) }
        ).flow
}