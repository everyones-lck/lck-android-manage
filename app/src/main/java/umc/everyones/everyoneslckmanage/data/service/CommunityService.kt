package umc.everyones.everyoneslckmanage.data.service

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.community.PageableRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommentWithReportListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityReportCauseResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityWithReportListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.ReadCommunityWithReportResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto

interface CommunityService {

    //게시글 목록 신고수와 함께 조회 (목록)
    @GET("admins/posts/reports")
    suspend fun getCommunityWithReportList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>?,
        @Query("postType") postType: String
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

    //게시글 삭제
    @DELETE("admins/posts/{post_id}")
    suspend fun deleteCommunityPost(
        @Path("post_id") postId: Long
    ): BaseResponse<Unit>

    //댓글 목록 신고수와 함께 조회 (목록)
    @GET("admins/comments/reports")
    suspend fun getCommentWithReportList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>?,
       // @Query("postType") postType: String
    ): BaseResponse<CommentWithReportListResponseDto>

    //댓글 삭제
    @DELETE("admins/comments/{comment_id}")
    suspend fun deleteCommunityComment(
        @Path("comment_id") commentId: Long
    ): BaseResponse<Unit>

}