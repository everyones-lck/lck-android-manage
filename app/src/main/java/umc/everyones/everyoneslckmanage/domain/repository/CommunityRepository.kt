package umc.everyones.everyoneslckmanage.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityListModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityReportListModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityResponseModel


interface CommunityRepository {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): Result<CommunityListModel>
    suspend fun fetchCommunityPost(postId: Long): Result<ReadCommunityResponseModel>
    suspend fun getCommunityReportList(size: Int, page: Int, type: String): Result<CommunityReportListModel>
    suspend fun deleteCommunityPost(postId: Long): Result<Unit>
    suspend fun deleteCommunityComment(commentId: Long): Result<Unit>
    fun fetchPagingSource(category: String): Flow<PagingData<CommunityListModel.CommunityListElementModel>>
}