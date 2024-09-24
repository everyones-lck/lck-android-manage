package umc.everyones.everyoneslckmanage.data.repositoryImpl

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import umc.everyones.everyoneslckmanage.data.datasource.CommunityDataSource
import umc.everyones.everyoneslckmanage.data.datasourceImpl.CommunityListPagingSource
import umc.everyones.everyoneslckmanage.data.service.CommunityService
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityListModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.everyoneslckmanage.domain.repository.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityDataSource: CommunityDataSource,
    private val communityService: CommunityService,
    private val spf: SharedPreferences
): CommunityRepository {
    override suspend fun fetchCommunityList(
        postType: String,
        page: Int,
        size: Int
    ): Result<CommunityListModel> =
        runCatching {
            communityDataSource.fetchCommunityList(postType, page, size).data.toCommunityListModel()
        }

    override suspend fun fetchCommunityPost(postId: Long): Result<ReadCommunityResponseModel> =
        runCatching {
            communityDataSource.fetchCommunityPost(postId).data.toReadCommunityResponseModel(spf.getString("nickname","")?:"")
        }


    override fun fetchPagingSource(category: String): Flow<PagingData<CommunityListModel.CommunityListElementModel>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = { CommunityListPagingSource(communityService, category) }
        ).flow
}