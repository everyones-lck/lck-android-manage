package umc.everyones.everyoneslckmanage.domain.model.response.community

import umc.everyones.everyoneslckmanage.data.dto.response.community.CommentWithReportListResponseDto.CommentWithReportListResponseElementDto
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityWithReportListModel
import kotlin.collections.map

data class CommentWithReportListResponseModel(
    val commentDetailList: List<CommentWithReportListResponseElementModel>,
    val isLast: Boolean
) {
    data class CommentWithReportListResponseElementModel(
        val commentId: Long,
        val content: String,
        val createdAt: String,
        val userNickname: String,
        val supportTeamName: String,
        val userProfilePicture: String,
        val postId: Long,
        val postTitle: String,
        val reportCounts: Int
    )
}