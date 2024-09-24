package umc.everyones.lck.data.dto.response.community

import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.everyoneslckmanage.util.extension.combineNicknameAndTeam
import umc.everyones.everyoneslckmanage.util.extension.toListViewingPartyDateFormat
import umc.everyones.everyoneslckmanage.util.extension.toReadDateFormat

data class ReadCommunityResponseDto(
    val postType: String,
    val writerProfileUrl: String,
    val writerNickname: String,
    val writerTeam: String,
    val postTitle: String,
    val postCreatedAt: String,
    val content: String,
    val fileList: List<File>,
    val commentList: List<CommentListElementDto>
) {
    data class CommentListElementDto(
        val profileUrl: String,
        val nickname: String,
        val supportTeam: String,
        val content: String,
        val createdAt: String,
        val commentId: Long
    ) {
        fun toCommentListElementModel(userNickname: String) =
            ReadCommunityResponseModel.CommentListElementModel(
                profileUrl,
                nickname.combineNicknameAndTeam(supportTeam),
                content,
                createdAt.slice(0..15).toListViewingPartyDateFormat(),
                commentId,
                if(userNickname == nickname) true else false
            )
    }

    fun toReadCommunityResponseModel(userNickname: String) =
        ReadCommunityResponseModel(
            postType,
            writerProfileUrl,
            writerNickname.combineNicknameAndTeam(writerTeam),
            postTitle,
            postCreatedAt.slice(0..15).toReadDateFormat(),
            content,
            fileList,
            commentList.map { it.toCommentListElementModel(userNickname) })
    data class File(
        val fileUrl: String,
        val isImage: Boolean
    )
}
