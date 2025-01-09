package umc.everyones.everyoneslckmanage.domain.repository

import umc.everyones.everyoneslckmanage.domain.model.request.match.InputMatchModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.CommonResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel

interface InputMatchRepository {
    suspend fun fetchInputMatch(request: InputMatchModel): Result<CommonResponseModel>
    suspend fun fetchMatchInfo(): Result<MatchInfoModel>
    suspend fun fetchLckMatchDetails(searchDate: String): Result<LckMatchDetailsModel>
}