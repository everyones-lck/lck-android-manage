package umc.everyones.everyoneslckmanage.data.dto

data class BaseResponse<T>(
    val status: Int,
    val code: String,
    val message: String,
    val data: T,
)