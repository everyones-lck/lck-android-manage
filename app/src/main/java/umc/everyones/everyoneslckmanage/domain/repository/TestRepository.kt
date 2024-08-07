package umc.everyones.everyoneslckmanage.domain.repository

import umc.everyones.everyoneslckmanage.data.dto.request.TestRequest
import umc.everyones.everyoneslckmanage.domain.model.TestModel
import umc.everyones.everyoneslckmanage.util.network.NetworkResult

interface TestRepository {
    suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel>
}