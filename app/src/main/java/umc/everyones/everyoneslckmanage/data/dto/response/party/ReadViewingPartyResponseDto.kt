package umc.everyones.everyoneslckmanage.data.dto.response.party

import umc.everyones.everyoneslckmanage.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.everyoneslckmanage.util.extension.combineNicknameAndTeam
import umc.everyones.everyoneslckmanage.util.extension.toReadDateFormat


data class ReadViewingPartyResponseDto(
    val name: String,
    val ownerName: String,
    val ownerTeam: String,
    val ownerImage: String,
    val qualify: String,
    val partyDate: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val price: String,
    val lowParticipate: String,
    val highParticipate: String,
    val etc: String,
    val participated: Boolean
) {
    fun toReadViewingPartyModel() =
        ReadViewingPartyModel(
            name,
            ownerName.combineNicknameAndTeam(ownerTeam),
            ownerImage,
            qualify,
            partyDate.slice(0..15).toReadDateFormat(),
            location,
            latitude,
            longitude,
            price,
            "$lowParticipate - $highParticipate 명",
            etc,
            participated
        )
}
