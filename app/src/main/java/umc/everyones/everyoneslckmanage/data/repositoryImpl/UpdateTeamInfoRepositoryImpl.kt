package umc.everyones.everyoneslckmanage.data.repositoryImpl

import okhttp3.MultipartBody
import okhttp3.RequestBody
import umc.everyones.everyoneslckmanage.data.datasource.UpdateTeamInfoDataSource
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerAddRequestDto
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerDeleteRequestDto
import umc.everyones.everyoneslckmanage.domain.model.response.team.PlayerListModel
import umc.everyones.everyoneslckmanage.domain.repository.UpdateTeamInfoRepository
import javax.inject.Inject

class UpdateTeamInfoRepositoryImpl @Inject constructor(
    private val dataSource: UpdateTeamInfoDataSource
) : UpdateTeamInfoRepository {

    override suspend fun getTeamPlayers(
        teamId: Int,
        season: String,
        role: String
    ): Result<PlayerListModel> = runCatching {
        val response = dataSource.getTeamPlayers(teamId, season, role)
        response.data.toPlayerListModel()
    }

    override suspend fun deletePlayer(
        request: PlayerDeleteRequestDto
    ):Result<Unit> = runCatching {
        dataSource.deletePlayer(request)
    }

    override suspend fun addPlayer(
        profileImage: MultipartBody.Part?,
        request: RequestBody
    ): Result<Unit> = runCatching {
        dataSource.addPlayer(profileImage, request)
    }
}