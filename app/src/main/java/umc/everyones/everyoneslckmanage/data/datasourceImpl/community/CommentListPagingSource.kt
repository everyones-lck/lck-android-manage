package umc.everyones.everyoneslckmanage.data.datasourceImpl.community

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import umc.everyones.everyoneslckmanage.data.service.CommunityService
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommentWithReportListResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityListModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityWithReportListModel
import javax.inject.Inject

class CommentListPagingSource @Inject constructor(
    private val communityService: CommunityService,
    private val category: String
) : PagingSource<Int, CommentWithReportListResponseModel.CommentWithReportListResponseElementModel>() {
    override fun getRefreshKey(state: PagingState<Int,CommentWithReportListResponseModel.CommentWithReportListResponseElementModel>): Int? {
        return 0/*state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }*/
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,CommentWithReportListResponseModel.CommentWithReportListResponseElementModel> {
        val page = params.key ?: 0
        if(page != 0) delay(100L)
        runCatching {
            delay(300L)
            communityService.getCommentWithReportList(page, 10, null,/*category*/).data.toCommentWithReportListResponseModel()
        }.fold(
            onSuccess = { response ->
                return LoadResult.Page(
                    data = response.commentDetailList,
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