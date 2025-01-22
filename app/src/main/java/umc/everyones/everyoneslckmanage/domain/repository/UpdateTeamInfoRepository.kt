package umc.everyones.everyoneslckmanage.domain.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerAddRequestDto
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerDeleteRequestDto
import umc.everyones.everyoneslckmanage.domain.model.response.team.PlayerListModel

interface UpdateTeamInfoRepository {
    suspend fun getTeamPlayers(teamId: Int, season: String, role: String): Result<PlayerListModel>
    suspend fun deletePlayer(request: PlayerDeleteRequestDto): Result<Unit>
    suspend fun addPlayer(profileImage: MultipartBody.Part?, request: RequestBody): Result<Unit>
}

