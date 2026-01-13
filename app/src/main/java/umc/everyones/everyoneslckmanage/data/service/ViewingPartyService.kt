package umc.everyones.everyoneslckmanage.data.service

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.dto.response.party.ReadViewingPartyResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.party.ViewingPartyListResponseDto
import umc.everyones.everyoneslckmanage.data.dto.response.party.ViewingPartyWithReportListResponseDto


interface ViewingPartyService {

    @GET("admins/viewing-parties")
    suspend fun getViewingPartyWithReportList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<ViewingPartyWithReportListResponseDto>

    @GET("viewing/{viewing_party_id}/detail")
    suspend fun fetchViewingParty(
        @Path("viewing_party_id") viewingPartyId: Long
    ): BaseResponse<ReadViewingPartyResponseDto>

    @DELETE("admins/viewing-parties/{viewing_party_id}")
    suspend fun deleteViewingParty(
        @Path("viewing_party_id") viewingPartyId: Long
    ): BaseResponse<Unit>
}