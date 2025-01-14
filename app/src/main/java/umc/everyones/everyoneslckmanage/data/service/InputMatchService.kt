package umc.everyones.everyoneslckmanage.data.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.match.InputMatchRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.InputMatchResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.LckMatchDetailsResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.match.MatchInfoResponseDto

interface InputMatchService {
    @POST("admins/matches")
    suspend fun  fetchInputMatch(
        @Body request: InputMatchRequestDto
    ): BaseResponse<InputMatchResponseDto>

    @GET("match/today/information")
    suspend fun fetchMatchInfo(): BaseResponse<MatchInfoResponseDto>

    @GET("aboutlck/match")
    suspend fun fetchLckMatchDetails(
        @Query("searchDate") searchDate: String // "yyyy-mm-dd" 형식의 날짜
    ): BaseResponse<LckMatchDetailsResponseDto>
}