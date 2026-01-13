package umc.everyones.everyoneslckmanage.data.datasourceImpl.party

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import umc.everyones.everyoneslckmanage.data.service.ViewingPartyService
import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyListModel
import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyWithReportListModel
import javax.inject.Inject

class ViewingPartyListPagingSource @Inject constructor(
    private val viewingPartyService: ViewingPartyService
) : PagingSource<Int, ViewingPartyWithReportListModel.ViewingPartyWithReportListElementModel>() {
    override fun getRefreshKey(state: PagingState<Int,  ViewingPartyWithReportListModel.ViewingPartyWithReportListElementModel>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,  ViewingPartyWithReportListModel.ViewingPartyWithReportListElementModel> {
        val page = params.key ?: 0
        if(page != 0) delay(100L)
        runCatching {
            delay(300L)
            viewingPartyService.getViewingPartyWithReportList(page, 10).data.toViewingPartyWithReportListModel()
        }.fold(
            onSuccess = { response ->
                return LoadResult.Page(
                    data = response.partyList,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.isLast) null else page + 1
                )
            }, onFailure = {
                Log.d("Paging 3 Load Error", it.stackTraceToString())
                return LoadResult.Error(it)
            }
        )
    }
}
