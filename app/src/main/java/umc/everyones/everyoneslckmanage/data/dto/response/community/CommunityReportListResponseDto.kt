package umc.everyones.everyoneslckmanage.data.dto.response.community

import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityListModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityReportListModel
import kotlin.collections.map

data class CommunityReportListResponseDto(
    val postDetailList: List<CommunityReportListElementDto>,
    val isLast: Boolean
) {
    data class CommunityReportListElementDto(
        val postId: Long,
        val postTitle: String,
        val postCreatedAt: String,
        val userNickname: String,
        val supportTeamName: String,
        val userProfilePicture: String,
        val thumbnailFileUrl: String,
        val commentCounts: Int,
        val reportCounts: Int
    ) {
        fun toCommunityReportListElementModel() =
            CommunityReportListModel.CommunityReportListElementModel(
                postId,
                postTitle,
                postCreatedAt,
                userNickname,
                if (supportTeamName == "empty") "" else supportTeamName,
                userProfilePicture,
                thumbnailFileUrl,
                commentCounts,
                reportCounts
            )
    }

    fun toCommunityListModel() =
        CommunityReportListModel(postDetailList.map { it.toCommunityReportListElementModel() }, isLast)
}