package umc.everyones.everyoneslckmanage.domain.model.response.party

data class ViewingPartyWithReportListModel(
    val viewingList: List<ViewingPartyWithReportListElementModel>,
    val size: Int,
    val isLast: Boolean
) {
    data class ViewingPartyWithReportListElementModel(
        val id: Long,
        val name: String,
        val userName: String,
        val teamName: String,
        val photoURL: String,
        val partyDate: String,
        val latitude: Double,
        val longitude: Double,
        val location: String,
        val shortLocation: String?,
        val reportCount: Int
    )
}
