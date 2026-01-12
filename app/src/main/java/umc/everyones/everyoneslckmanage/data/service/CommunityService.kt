package umc.everyones.everyoneslckmanage.data.service

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.community.PageableRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityReportCauseResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityWithReportListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.ReadCommunityWithReportResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto

interface CommunityService {
    @GET("post/list")
    suspend fun fetchCommunityList(
        @Query("postType") postType: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): BaseResponse<CommunityListResponseDto>
    @GET("post/{postId}/detail")
    suspend fun fetchCommunityPost(
        @Path("postId") postId: Long
    ): BaseResponse<ReadCommunityResponseDto>

    //게시글 목록 신고수와 함께 조회 (목록)
    @GET("admins/posts/reports")
    suspend fun getCommunityWithReportList(
        @Path("pageable") pageable: PageableRequestDto,
        @Path("postType") postType: String
    ): BaseResponse<CommunityWithReportListResponseDto>

    //게시글 신고수와 함께 조회 (상세보기)
    @GET("admins/posts/{postId}/reports")
    suspend fun getCommunityWithReport(
        @Path("postId") postId: Long
    ):BaseResponse<ReadCommunityWithReportResponseDto>

    //게시글 신고 사유 조회
    @GET("admins/posts/{postId}/reports/causes")
    suspend fun getCommunityReportCause(
        @Path("postId") postId: Long
    ):BaseResponse<CommunityReportCauseResponseDto>

    @DELETE("admins/posts/{post_id}")
    suspend fun deleteCommunityPost(
        @Path("post_id") postId: Long
    ): BaseResponse<Unit>

    @DELETE("admins/comments/{comment_id}")
    suspend fun deleteCommunityComment(
        @Path("comment_id") commentId: Long
    ): BaseResponse<Unit>


}