package umc.everyones.everyoneslckmanage.data.datasourceImpl.community

import umc.everyones.everyoneslckmanage.data.datasource.CommunityDataSource
import umc.everyones.everyoneslckmanage.data.dto.BaseResponse
import umc.everyones.everyoneslckmanage.data.service.CommunityService
import umc.everyones.everyoneslckmanage.data.dto.response.community.CommunityListResponseDto
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto
import javax.inject.Inject

class CommunityDataSourceImpl @Inject constructor(
    private val communityService: CommunityService
): CommunityDataSource {
    override suspend fun fetchCommunityList(
        postType: String,
        page: Int,
        size: Int
    ): BaseResponse<CommunityListResponseDto> =
        communityService.fetchCommunityList(postType, page, size)


    override suspend fun fetchCommunityPost(postId: Long): BaseResponse<ReadCommunityResponseDto> =
        communityService.fetchCommunityPost(postId)



}