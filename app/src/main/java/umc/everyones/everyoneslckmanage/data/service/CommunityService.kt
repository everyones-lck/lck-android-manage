package umc.everyones.everyoneslckmanage.data.service

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.community.PageableRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityWithReportListResponseDto
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

    @GET("admins/posts/reports")
    suspend fun getCommunityWithReportList(
        @Path("pageable") pageable: PageableRequestDto,
        @Path("postType") postType: String
    ): BaseResponse<CommunityWithReportListResponseDto>

/*    @GET("admins/posts/reports?size={size}&page={page}&postType={type}")
    suspend fun getCommunityReportList(
        @Path("size") size: Int,
        @Path("page") page: Int,
        @Path("type") type: String
    ): BaseResponse<CommunityWithReportListResponseDto>
    */
    @DELETE("admins/posts/{post_id}")
    suspend fun deleteCommunityPost(
        @Path("post_id") postId: Long
    ): BaseResponse<Unit>

    @DELETE("admins/comments/{comment_id}")
    suspend fun deleteCommunityComment(
        @Path("comment_id") commentId: Long
    ): BaseResponse<Unit>


}