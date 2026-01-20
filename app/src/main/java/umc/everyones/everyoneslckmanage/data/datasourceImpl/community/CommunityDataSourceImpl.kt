package umc.everyones.everyoneslckmanage.data.datasourceImpl.community

import umc.everyones.everyoneslckmanage.data.datasource.CommunityDataSource
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.community.PageableRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommentWithReportListResponseDto
import umc.everyones.everyoneslckmanage.data.service.CommunityService
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityReportCauseResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityWithReportListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.ReadCommunityWithReportResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto
import javax.inject.Inject

class CommunityDataSourceImpl @Inject constructor(
    private val communityService: CommunityService
): CommunityDataSource {

    override suspend fun getCommunityWithReportList(pageable: PageableRequestDto, postType: String): BaseResponse<CommunityWithReportListResponseDto> =
        communityService.getCommunityWithReportList(pageable.page, pageable.size, pageable.sort, postType)

    override suspend fun getCommunityWithReport(postId: Long): BaseResponse<ReadCommunityWithReportResponseDto> =
        communityService.getCommunityWithReport(postId)

    override suspend fun getCommunityReportCause(postId: Long): BaseResponse<CommunityReportCauseResponseDto> =
        communityService.getCommunityReportCause(postId)

    override suspend fun deleteCommunityPost(postId: Long): BaseResponse<Unit> =
        communityService.deleteCommunityPost(postId)

    override suspend fun getCommentWithReportList(pageable: PageableRequestDto/*, postType: String*/): BaseResponse<CommentWithReportListResponseDto> =
        communityService.getCommentWithReportList(pageable.page, pageable.size, pageable.sort/*, postType*/)

    override suspend fun deleteCommunityComment(commentId: Long): BaseResponse<Unit> =
        communityService.deleteCommunityComment(commentId)

}