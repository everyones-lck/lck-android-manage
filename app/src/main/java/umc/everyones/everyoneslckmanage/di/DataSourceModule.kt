package umc.everyones.everyoneslckmanage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import umc.everyones.everyoneslckmanage.data.datasource.CommunityDataSource
import umc.everyones.everyoneslckmanage.data.datasourceImpl.CommunityDataSourceImpl

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {

    @Provides
    @ViewModelScoped
    fun provideCommunityDataSource(communityDataSourceImpl: CommunityDataSourceImpl): CommunityDataSource =
        communityDataSourceImpl


}