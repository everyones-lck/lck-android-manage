package umc.everyones.everyoneslckmanage.domain.model.response.community

data class CommunityWithReportListModel(
    val postDetailList: List<CommunityReportListElementModel>,
    val isLast: Boolean
){
    data class CommunityReportListElementModel(
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
    )
}