package umc.everyones.everyoneslckmanage.data.datasource

import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityReportListResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto

interface CommunityDataSource {
    suspend fun fetchCommunityList(postType: String, page: Int, size: Int): BaseResponse<CommunityListResponseDto>
    suspend fun fetchCommunityPost(postId: Long): BaseResponse<ReadCommunityResponseDto>
    suspend fun getCommunityReportList(size: Int, page: Int, type: String): BaseResponse<CommunityReportListResponseDto>
    suspend fun deleteCommunityPost(postId: Long): BaseResponse<Unit>
    suspend fun deleteCommunityComment(commentId: Long): BaseResponse<Unit>
}