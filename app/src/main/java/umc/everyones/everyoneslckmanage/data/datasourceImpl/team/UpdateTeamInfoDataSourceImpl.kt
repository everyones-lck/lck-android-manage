package umc.everyones.everyoneslckmanage.data.datasourceImpl.team

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.everyoneslckmanage.data.datasource.UpdateTeamInfoDataSource
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerAddRequestDto
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerDeleteRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.team.PlayerListResponseDto
import umc.everyones.everyoneslckmanage.data.service.UpdateTeamInfoService
import javax.inject.Inject

class UpdateTeamInfoDataSourceImpl @Inject constructor(
    private val updateTeamInfoService: UpdateTeamInfoService
) : UpdateTeamInfoDataSource {

    override suspend fun getTeamPlayers(
        teamId: Int,
        season: String,
        role: String
    ): BaseResponse<PlayerListResponseDto> =
        updateTeamInfoService.getTeamPlayers(teamId, season, role)

    override suspend fun deletePlayer(
        request: PlayerDeleteRequestDto
    ): BaseResponse<Unit> =
        updateTeamInfoService.deletePlayer(request)

    override suspend fun addPlayer(
        profileImage: MultipartBody.Part?,
        request: RequestBody
    ): BaseResponse<Unit> =
        updateTeamInfoService.addPlayer(profileImage, request)
}
