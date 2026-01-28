package umc.everyones.everyoneslckmanage.data.dto.response.party

import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyWithReportListModel
import umc.everyones.everyoneslckmanage.util.extension.combineNicknameAndTeam
import umc.everyones.everyoneslckmanage.util.extension.toListViewingPartyDateFormat

data class ViewingPartyWithReportListResponseDto(
    val viewingList: List<ViewingPartyWithReportListElementDto>,
    val size: Int,
    val isLast: Boolean
) {
    data class ViewingPartyWithReportListElementDto(
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
    ){
        fun toViewingPartyWithReportListElementModel() =
            ViewingPartyWithReportListModel.ViewingPartyWithReportListElementModel(id, name, userName,teamName, photoURL,partyDate.toListViewingPartyDateFormat(), latitude, longitude, location, shortLocation, reportCount)
    }

    fun toViewingPartyWithReportListModel() =
        ViewingPartyWithReportListModel(viewingList.map { it.toViewingPartyWithReportListElementModel() }, size, isLast)
}
