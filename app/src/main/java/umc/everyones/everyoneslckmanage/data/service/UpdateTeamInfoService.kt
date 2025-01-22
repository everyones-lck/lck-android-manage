package umc.everyones.everyoneslckmanage.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerAddRequestDto
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerDeleteRequestDto
import umc.everyones.everyoneslckmanage.data.dto.response.team.PlayerListResponseDto

interface UpdateTeamInfoService {
    @GET("admins/teams/{teamId}/players")
    suspend fun getTeamPlayers(
        @Path("teamId") teamId: Int,
        @Query("season") season: String,
        @Query("role") role: String
    ): BaseResponse<PlayerListResponseDto>

    @Multipart
    @POST("admins/players")
    suspend fun addPlayer(
        @Part profileImage: MultipartBody.Part?,
        @Part("request") request: RequestBody
    ): BaseResponse<Unit>

    @HTTP(method = "DELETE", path="admins/players", hasBody = true)
    suspend fun deletePlayer(
        @Body request: PlayerDeleteRequestDto
    ): BaseResponse<Unit>
}