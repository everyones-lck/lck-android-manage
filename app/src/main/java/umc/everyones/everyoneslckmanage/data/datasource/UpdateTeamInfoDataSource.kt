package umc.everyones.everyoneslckmanage.data.datasource

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.response.team.PlayerListResponseDto

interface UpdateTeamInfoDataSource {
    suspend fun getTeamPlayers(teamId: Int, season: String, role: String): BaseResponse<PlayerListResponseDto>
    suspend fun deletePlayer(playerId : Long): BaseResponse<Unit>
    suspend fun addPlayer(profileImage: MultipartBody.Part?, request: RequestBody): BaseResponse<Unit>
    suspend fun updatePlayer(profileImage: MultipartBody.Part?, request:RequestBody): BaseResponse<Unit>
}

