package umc.everyones.everyoneslckmanage.domain.model.response.community

import umc.everyones.everyoneslckmanage.data.dto.response.community.ReadCommunityWithReportResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto

data class ReadCommunityWithReportResponseModel(
    val postType: String,
    val writerProfileUrl: String,
    val writerNickname: String,
    val writerTeam: String,
    val postTitle: String,
    val postCreatedAt: String,
    val content: String,
    val reportCount: Int,
    val fileList: List<ReadCommunityWithReportResponseDto.File>,
    val commentList: List<CommentListElementModel>
) {
    data class CommentListElementModel(
        val profileImageUrl: String?,
        val nickname: String,
        val supportTeam: String?,
        val content: String,
        val createdAt: String,
        val commentId: Long,
        val reportCount: Int
    )
}
