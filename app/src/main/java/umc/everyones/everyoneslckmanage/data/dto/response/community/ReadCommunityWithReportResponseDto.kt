package umc.everyones.everyoneslckmanage.data.dto.response.community

import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityWithReportResponseModel
import umc.everyones.everyoneslckmanage.util.extension.combineNicknameAndTeam
import umc.everyones.everyoneslckmanage.util.extension.toListViewingPartyDateFormat
import umc.everyones.everyoneslckmanage.util.extension.toReadDateFormat

data class ReadCommunityWithReportResponseDto(
    val postType: String,
    val writerProfileUrl: String,
    val writerNickname: String,
    val writerTeam: String,
    val postTitle: String,
    val postCreatedAt: String,
    val content: String,
    val reportCount: Int,
    //val isWriter: Boolean,
    val fileList: List<File>,
    val commentList: List<CommentListElementDto>
) {
    data class CommentListElementDto(
        val profileUrl: String,
        val nickname: String,
        val supportTeam: String,
        val content: String,
        val createdAt: String,
        val commentId: Long,
        val reportCount: Int
    ) {
        fun toCommentListElementModel(userNickname: String) =
            ReadCommunityWithReportResponseModel.CommentListElementModel(
                profileUrl,
                nickname.combineNicknameAndTeam(supportTeam),
                content,
                createdAt.slice(0..15).toListViewingPartyDateFormat(),
                createdAt,
                commentId,
                reportCount
            )
    }

    fun toReadCommunityWithReportResponseModel(userNickname: String) =
        ReadCommunityWithReportResponseModel(
            postType,
            writerProfileUrl,
            writerNickname.combineNicknameAndTeam(writerTeam),
            writerTeam,
            postTitle,
            postCreatedAt.slice(0..15).toReadDateFormat(),
            content,
            reportCount,
            fileList,
            commentList.map { it.toCommentListElementModel(userNickname) })
    data class File(
        val fileUrl: String,
        val isImage: Boolean
    )
}
