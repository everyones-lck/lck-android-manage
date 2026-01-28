package umc.everyones.everyoneslckmanage.domain.repository

import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.domain.model.request.LoginRequestModel
import umc.everyones.everyoneslckmanage.domain.model.response.login.LoginResponseModel

interface LoginRepository {
    suspend fun login(requestModel: LoginRequestModel): Result<LoginResponseModel>
}