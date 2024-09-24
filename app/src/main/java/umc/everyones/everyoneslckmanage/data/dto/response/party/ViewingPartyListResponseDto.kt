package umc.everyones.everyoneslckmanage.data.dto.response.party

import umc.everyones.everyoneslckmanage.util.extension.combineNicknameAndTeam
import umc.everyones.everyoneslckmanage.util.extension.toListViewingPartyDateFormat
import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyListModel


data class ViewingPartyListResponseDto(
    val isLast: Boolean,
    val totalPage: Int,
    val partyList: List<ViewingPartyListElementDto>
) {
    data class ViewingPartyListElementDto(
        val id: Long,
        val name: String,
        val userName: String,
        val teamName: String,
        val photoURL: String,
        val partyDate: String,
        val latitude: Double,
        val longitude: Double,
        val location: String,
        val shortLocation: String?
    ){
        fun toViewingPartyListElementModel() =
            ViewingPartyListModel.ViewingPartyElementModel(
                id,
                name,
                userName.combineNicknameAndTeam(teamName),
                photoURL,
                partyDate.slice(0..15).toListViewingPartyDateFormat(),
                latitude,
                longitude,
                location,
                shortLocation
            )
    }

    fun toViewingPartyListModel() =
        ViewingPartyListModel(isLast, totalPage, partyList.map { it.toViewingPartyListElementModel() })
}
