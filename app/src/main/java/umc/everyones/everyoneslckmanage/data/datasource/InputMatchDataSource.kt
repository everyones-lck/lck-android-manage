package umc.everyones.everyoneslckmanage.data.datasource

import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.match.InputMatchRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.InputMatchResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.LckMatchDetailsResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.MatchInfoResponseDto

interface InputMatchDataSource {
    suspend fun fetchInputMatch(request: InputMatchRequestDto): BaseResponse<InputMatchResponseDto>
    suspend fun fetchMatchInfo(): BaseResponse<MatchInfoResponseDto>
    suspend fun fetchLckMatchDetails(searchDate: String): BaseResponse<LckMatchDetailsResponseDto>
}