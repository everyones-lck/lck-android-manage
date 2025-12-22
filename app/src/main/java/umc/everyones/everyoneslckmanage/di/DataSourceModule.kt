package umc.everyones.everyoneslckmanage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import umc.everyones.everyoneslckmanage.data.datasource.CommunityDataSource
import umc.everyones.everyoneslckmanage.data.datasource.InputMatchDataSource
import umc.everyones.everyoneslckmanage.data.datasource.ViewingPartyDataSource
import umc.everyones.everyoneslckmanage.data.datasourceImpl.community.CommunityDataSourceImpl
import umc.everyones.everyoneslckmanage.data.datasourceImpl.match.InputMatchDataSourceImpl
import umc.everyones.everyoneslckmanage.data.datasourceImpl.party.ViewingPartyDataSourceImpl

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {

    @Provides
    @ViewModelScoped
    fun provideCommunityDataSource(communityDataSourceImpl: CommunityDataSourceImpl): CommunityDataSource =
        communityDataSourceImpl

    @Provides
    @ViewModelScoped
    fun provideViewingPartyDataSource(viewingPartyDataSourceImpl: ViewingPartyDataSourceImpl): ViewingPartyDataSource =
        viewingPartyDataSourceImpl

    @Provides
    @ViewModelScoped
    fun provideInputMatchDataSource(inputMatchDataSourceImpl: InputMatchDataSourceImpl): InputMatchDataSource =
        inputMatchDataSourceImpl
}