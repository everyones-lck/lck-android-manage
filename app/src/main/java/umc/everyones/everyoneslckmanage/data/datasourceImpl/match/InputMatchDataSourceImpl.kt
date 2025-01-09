package umc.everyones.everyoneslckmanage.data.datasourceImpl.match

import umc.everyones.everyoneslckmanage.data.datasource.InputMatchDataSource
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.match.InputMatchRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.InputMatchResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.LckMatchDetailsResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.MatchInfoResponseDto
import umc.everyones.everyoneslckmanage.data.service.InputMatchService
import javax.inject.Inject

class InputMatchDataSourceImpl @Inject constructor(
    private val inputMatchService: InputMatchService
): InputMatchDataSource {
    override suspend fun fetchInputMatch(request: InputMatchRequestDto): BaseResponse<InputMatchResponseDto> =
        inputMatchService.fetchInputMatch(request)

    override suspend fun fetchMatchInfo(): BaseResponse<MatchInfoResponseDto> =
        inputMatchService.fetchMatchInfo()

    override suspend fun fetchLckMatchDetails(
        searchDate: String
    ): BaseResponse<LckMatchDetailsResponseDto> =
        inputMatchService.fetchLckMatchDetails(searchDate)
}