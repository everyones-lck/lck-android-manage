package umc.everyones.everyoneslckmanage.data.datasource

import com.google.android.exoplayer2.analytics.PlayerId
import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerAddRequestDto
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerDeleteRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.team.PlayerListResponseDto

interface UpdateTeamInfoDataSource {
    suspend fun getTeamPlayers(teamId: Int, season: String, role: String): BaseResponse<PlayerListResponseDto>
    suspend fun deletePlayer(request: PlayerDeleteRequestDto  ): BaseResponse<Unit>
    suspend fun addPlayer(profileImage: MultipartBody.Part?, request: RequestBody): BaseResponse<Unit>
}

