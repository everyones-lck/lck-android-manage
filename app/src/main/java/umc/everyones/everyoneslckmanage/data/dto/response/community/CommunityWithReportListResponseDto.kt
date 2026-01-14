package umc.everyones.everyoneslckmanage.data.dto.response.community

import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityWithReportListModel
import kotlin.collections.map

data class CommunityWithReportListResponseDto(
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
        val reportCounts: Int,
        val commentReportCounts: Int
    ) {
        fun toCommunityReportListElementModel() =
            CommunityWithReportListModel.CommunityReportListElementModel(
                postId,
                postTitle,
                postCreatedAt,
                userNickname,
                if (supportTeamName == "empty") "" else supportTeamName,
                userProfilePicture,
                thumbnailFileUrl,
                commentCounts,
                reportCounts,
                commentReportCounts
            )
    }

    fun toCommunityListModel() =
        CommunityWithReportListModel(postDetailList.map { it.toCommunityReportListElementModel() }, isLast)
}