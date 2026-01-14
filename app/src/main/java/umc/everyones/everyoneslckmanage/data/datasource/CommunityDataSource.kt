package umc.everyones.everyoneslckmanage.data.datasource

import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.community.PageableRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommentWithReportListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityReportCauseResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityWithReportListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.ReadCommunityWithReportResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto

interface CommunityDataSource {
    suspend fun getCommunityWithReportList(pageable: PageableRequestDto, postType: String): BaseResponse<CommunityWithReportListResponseDto>
    suspend fun getCommunityWithReport(postId: Long): BaseResponse<ReadCommunityWithReportResponseDto>
    suspend fun getCommunityReportCause(postId: Long): BaseResponse<CommunityReportCauseResponseDto>
    suspend fun deleteCommunityPost(postId: Long): BaseResponse<Unit>
    suspend fun getCommentWithReportList(pageable: PageableRequestDto/*, postType: String*/): BaseResponse<CommentWithReportListResponseDto>
    suspend fun deleteCommunityComment(commentId: Long): BaseResponse<Unit>
}