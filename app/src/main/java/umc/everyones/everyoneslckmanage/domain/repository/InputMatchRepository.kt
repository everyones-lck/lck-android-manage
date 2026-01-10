package umc.everyones.everyoneslckmanage.domain.repository

import umc.everyones.everyoneslckmanage.domain.model.request.match.CloseMatchModel
import umc.everyones.everyoneslckmanage.domain.model.request.match.CloseSetModel
import umc.everyones.everyoneslckmanage.domain.model.request.match.InputMatchModel
import umc.everyones.everyoneslckmanage.domain.model.request.match.MatchResultModel
import umc.everyones.everyoneslckmanage.domain.model.request.match.SetResultModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.CommonResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.SetResultInfoResponseModel

interface InputMatchRepository {
    suspend fun fetchInputMatch(request: InputMatchModel): Result<CommonResponseModel>
    suspend fun fetchMatchInfo(): Result<MatchInfoModel>
    suspend fun fetchLckMatchDetails(searchDate: String): Result<LckMatchDetailsModel>
    suspend fun fetchSetResults(request: SetResultModel): Result<CommonResponseModel>
    suspend fun fetchMatchResults(request: MatchResultModel): Result<CommonResponseModel>
    suspend fun fetchSetResultInfo(matchId: Long): Result<SetResultInfoResponseModel>
    suspend fun fetchCloseSets(request: CloseSetModel): Result<CommonResponseModel>
    suspend fun fetchCloseMatch(request: CloseMatchModel): Result<CommonResponseModel>
}