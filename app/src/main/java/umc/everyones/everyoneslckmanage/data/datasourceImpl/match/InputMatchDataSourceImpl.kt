package umc.everyones.everyoneslckmanage.data.datasourceImpl.match

import umc.everyones.everyoneslckmanage.data.datasource.InputMatchDataSource
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.match.CloseMatchRequestDto
import umc.everyones.everyoneslckmanage.data.dto.request.match.CloseSetRequestDto
import umc.everyones.everyoneslckmanage.data.dto.request.match.InputMatchRequestDto
import umc.everyones.everyoneslckmanage.data.dto.request.match.MatchResultRequestDto
import umc.everyones.everyoneslckmanage.data.dto.request.match.SetResultRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.InputMatchResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.LckMatchDetailsResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.MatchInfoResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.SetResultInfoResponseDto
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

    override suspend fun fetchSetResults(request: SetResultRequestDto): BaseResponse<InputMatchResponseDto> =
        inputMatchService.fetchSetResults(request)

    override suspend fun fetchMatchResults(request: MatchResultRequestDto): BaseResponse<InputMatchResponseDto> =
        inputMatchService.fetchMatchResults(request)

    override suspend fun fetchSetResultInfo(matchId: Long): BaseResponse<SetResultInfoResponseDto> =
        inputMatchService.fetchSetResultInfo(matchId)

    override suspend fun fetchCloseSets(request: CloseSetRequestDto): BaseResponse<InputMatchResponseDto> =
        inputMatchService.fetchCloseSets(request)

    override suspend fun fetchCloseMatch(request: CloseMatchRequestDto): BaseResponse<InputMatchResponseDto> =
        inputMatchService.fetchCloseMatch(request)
}