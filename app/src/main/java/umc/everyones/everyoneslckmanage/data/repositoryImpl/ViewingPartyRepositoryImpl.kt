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
import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyWithReportListModel
import umc.everyones.everyoneslckmanage.domain.repository.ViewingPartyRepository
import javax.inject.Inject

class ViewingPartyRepositoryImpl @Inject constructor(
    private val viewingPartyDataSource: ViewingPartyDataSource,
    private val viewingPartyService: ViewingPartyService,
    private val spf: SharedPreferences
) : ViewingPartyRepository {

    override suspend fun getViewingPartyWithReportList(page: Int, size: Int): Result<ViewingPartyWithReportListModel> =
        runCatching { viewingPartyDataSource.getViewingPartyWithReportList(page, size).data.toViewingPartyWithReportListModel() }

    override suspend fun fetchViewingParty(viewingPartyId: Long): Result<ReadViewingPartyModel> =
        runCatching {
            viewingPartyDataSource.fetchViewingParty(viewingPartyId).data.toReadViewingPartyModel()
        }

    override suspend fun deleteViewingParty(viewingPartyId: Long): Result<Unit> =
        runCatching {
            viewingPartyDataSource.deleteViewingParty(viewingPartyId)
        }

    override fun fetchViewingPartyListPagingSource(): Flow<PagingData<ViewingPartyWithReportListModel.ViewingPartyWithReportListElementModel>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { ViewingPartyListPagingSource(viewingPartyService) }
        ).flow
}