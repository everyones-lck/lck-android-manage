package umc.everyones.everyoneslckmanage.data.dto.response.match

import umc.everyones.everyoneslckmanage.domain.model.request.match.InputMatchModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.CommonResponseModel

data class InputMatchResponseDto (
    val message: String,
    val success: Boolean
) {
    fun toCommonResponseModel() =
        CommonResponseModel(message, success)
}