package umc.everyones.everyoneslckmanage.data.repositoryImpl

import umc.everyones.everyoneslckmanage.data.datasource.InputMatchDataSource
import umc.everyones.everyoneslckmanage.domain.model.request.match.InputMatchModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.CommonResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.domain.repository.InputMatchRepository
import javax.inject.Inject

class InputMatchRepositoryImpl @Inject constructor(
    private val inputMatchDataSource: InputMatchDataSource
): InputMatchRepository {
    override suspend fun fetchInputMatch(request: InputMatchModel): Result<CommonResponseModel> = runCatching {
        inputMatchDataSource.fetchInputMatch(request.toInputMatchRequestDto()).data.toCommonResponseModel()
    }

    override suspend fun fetchMatchInfo(): Result<MatchInfoModel> = runCatching {
        inputMatchDataSource.fetchMatchInfo().data.toMatchInfoModel()
    }

    override suspend fun fetchLckMatchDetails(
        searchDate: String
    ): Result<LckMatchDetailsModel> =
        runCatching {
            inputMatchDataSource.fetchLckMatchDetails(
                searchDate
            ).data.toAboutLckMatchDetailsModel()
        }
}