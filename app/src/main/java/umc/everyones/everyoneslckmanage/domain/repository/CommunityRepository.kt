package umc.everyones.everyoneslckmanage.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import umc.everyones.everyoneslckmanage.data.dto.request.community.PageableRequestDto
import umc.everyones.everyoneslckmanage.domain.model.request.community.PageableRequestModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommentWithReportListResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityListModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityReportCauseResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityWithReportListModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityWithReportResponseModel


interface CommunityRepository {
    suspend fun getCommunityWithReportList(pageable: PageableRequestModel, postType: String): Result<CommunityWithReportListModel>
    suspend fun getCommunityWithReport(postId: Long): Result<ReadCommunityWithReportResponseModel>
    suspend fun getCommunityReportCause(postId: Long): Result<CommunityReportCauseResponseModel>
    suspend fun deleteCommunityPost(postId: Long): Result<Unit>
    suspend fun getCommentWithReportList(pageable: PageableRequestModel/*, postType: String*/): Result<CommentWithReportListResponseModel>
    suspend fun deleteCommunityComment(commentId: Long): Result<Unit>
    fun fetchPagingSource(category: String): Flow<PagingData< CommunityWithReportListModel.CommunityReportListElementModel>>
    fun fetchCommentPagingSource(category: String): Flow<PagingData<CommentWithReportListResponseModel.CommentWithReportListResponseElementModel>>

}