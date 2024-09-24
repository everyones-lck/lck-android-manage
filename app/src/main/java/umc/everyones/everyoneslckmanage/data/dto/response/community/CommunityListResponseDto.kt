package umc.everyones.everyoneslckmanage.data.dto.response.community

import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityListModel

data class CommunityListResponseDto(
    val postDetailList: List<CommunityListElementDto>,
    val isLast: Boolean
) {
    data class CommunityListElementDto(
        val postId: Long,
        val postTitle: String,
        val postCreatedAt: String,
        val userNickname: String,
        val supportTeamName: String,
        val userProfilePicture: String,
        val commentCounts: Int,
        val thumbnailFileUrl: String
    ) {
        fun toCommunityListElementModel() =
            CommunityListModel.CommunityListElementModel(
                postId,
                postTitle,
                postCreatedAt,
                userNickname,
                if (supportTeamName == "empty") "" else supportTeamName,
                userProfilePicture,
                commentCounts,
                thumbnailFileUrl
            )
    }

    fun toCommunityListModel() =
        CommunityListModel(postDetailList.map { it.toCommunityListElementModel() }, isLast)
}