package umc.everyones.everyoneslckmanage.data.repositoryImpl

import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.TestRequest
import umc.everyones.everyoneslckmanage.data.dto.response.TestResponse
import umc.everyones.everyoneslckmanage.data.service.TestService
import umc.everyones.everyoneslckmanage.domain.model.TestModel
import umc.everyones.everyoneslckmanage.domain.repository.TestRepository
import umc.everyones.everyoneslckmanage.util.network.NetworkResult
import umc.everyones.everyoneslckmanage.util.network.handleApi
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testService: TestService
) : TestRepository {
    override suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel> {
        return handleApi({testService.fetchTest(request)}) {response: BaseResponse<TestResponse> -> response.data.toTestModel()} // mapper를 통해 api 결과를 TestModel로 매핑
    }

}