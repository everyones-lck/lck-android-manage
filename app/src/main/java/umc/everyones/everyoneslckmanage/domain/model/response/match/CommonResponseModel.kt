package umc.everyones.everyoneslckmanage.domain.model.response.match

data class CommonResponseModel(
    val message: String,
    val data: Any? = null,
    val success: Boolean
)
