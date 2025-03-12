package umc.everyones.everyoneslckmanage.data.repositoryImpl

import umc.everyones.everyoneslckmanage.data.datasource.InputMatchDataSource
import umc.everyones.everyoneslckmanage.domain.model.request.match.InputMatchModel
import umc.everyones.everyoneslckmanage.domain.model.request.match.MatchResultModel
import umc.everyones.everyoneslckmanage.domain.model.request.match.SetResultModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.CommonResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.SetResultInfoResponseModel
import umc.everyones.everyoneslckmanage.domain.repository.InputMatchRepository
import javax.inject.Inject

class InputMatchRepositoryImpl @Inject constructor(
    private val inputMatchDataSource: InputMatchDataSource
): InputMatchRepository {
    override suspend fun fetchInputMatch(request: InputMatchModel): Result<CommonResponseModel> = runCatching {
        val responseDto = inputMatchDataSource.fetchInputMatch(request.toInputMatchRequestDto())
        if (responseDto.data == null) {
            // data가 null이면, 서버 응답의 message와 success 값을 그대로 사용하여 CommonResponseModel 생성
            CommonResponseModel(responseDto.message, data = null, responseDto.success)
        } else {
            responseDto.data.toCommonResponseModel()
        }
    }


    override suspend fun fetchMatchInfo(): Result<MatchInfoModel> = runCatching {
        inputMatchDataSource.fetchMatchInfo().data.toMatchInfoModel()
    }

    override suspend fun fetchLckMatchDetails(searchDate: String): Result<LckMatchDetailsModel> = runCatching {
        inputMatchDataSource.fetchLckMatchDetails(searchDate).data.toAboutLckMatchDetailsModel()
    }

    override suspend fun fetchSetResults(request: SetResultModel): Result<CommonResponseModel> = runCatching {
        inputMatchDataSource.fetchSetResults(request.toSetResultRequestDto()).data.toCommonResponseModel()
    }

    override suspend fun fetchMatchResults(request: MatchResultModel): Result<CommonResponseModel> = runCatching {
        inputMatchDataSource.fetchMatchResults(request.toMatchResultRequestDto()).data.toCommonResponseModel()
    }

    override suspend fun fetchSetResultInfo(matchId: Long): Result<SetResultInfoResponseModel> = runCatching {
        inputMatchDataSource.fetchSetResultInfo(matchId).data.toSetResultInfoResponseModel()
    }
}