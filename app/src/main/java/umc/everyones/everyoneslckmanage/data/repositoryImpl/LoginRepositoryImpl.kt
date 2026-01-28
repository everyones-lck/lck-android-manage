package umc.everyones.everyoneslckmanage.data.repositoryImpl

import umc.everyones.everyoneslckmanage.data.datasource.LoginDataSource
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.domain.model.request.LoginRequestModel
import umc.everyones.everyoneslckmanage.domain.model.response.login.LoginResponseModel
import umc.everyones.everyoneslckmanage.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
): LoginRepository {
    override suspend fun login(requestModel: LoginRequestModel): Result<LoginResponseModel> =
        runCatching { loginDataSource.login(requestModel.toLoginRequestDto()).data.toLoginResponseModel() }
    }