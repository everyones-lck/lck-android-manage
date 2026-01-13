package umc.everyones.everyoneslckmanage.data.dto.response.community

import umc.everyones.everyoneslckmanage.domain.model.response.community.CommentWithReportListResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityWithReportListModel
import kotlin.collections.map

data class CommentWithReportListResponseDto(
    val commentDetailList: List<CommentWithReportListResponseElementDto>,
    val isLast: Boolean
){
    data class CommentWithReportListResponseElementDto(
        val commentId: Long,
        val content: String,
        val createdAt: String,
        val userNickname: String,
        val supportTeamName: String,
        val userProfilePicture: String,
        val postId: Long,
        val postTitle: String,
        val reportCounts: Int
    ){
        fun toCommentWithReportListResponseElementModel() =
            CommentWithReportListResponseModel.CommentWithReportListResponseElementModel(
                commentId,
                content,
                createdAt,
                userNickname,
                supportTeamName,
                userProfilePicture,
                postId,
                postTitle,
                reportCounts
            )
    }
    fun toCommentWithReportListResponseModel() =
        CommentWithReportListResponseModel(commentDetailList.map { it.toCommentWithReportListResponseElementModel() }, isLast)
}