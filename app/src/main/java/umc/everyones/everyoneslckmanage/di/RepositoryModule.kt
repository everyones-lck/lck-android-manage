package umc.everyones.everyoneslckmanage.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import umc.everyones.everyoneslckmanage.data.repositoryImpl.CommunityRepositoryImpl
import umc.everyones.everyoneslckmanage.data.repositoryImpl.UpdateTeamInfoRepositoryImpl
import umc.everyones.everyoneslckmanage.data.repositoryImpl.ViewingPartyRepositoryImpl
import umc.everyones.everyoneslckmanage.domain.repository.CommunityRepository
import umc.everyones.everyoneslckmanage.domain.repository.UpdateTeamInfoRepository
import umc.everyones.everyoneslckmanage.domain.repository.ViewingPartyRepository
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application

    // 스코프 애노테이션이 있음
    // 해당하는 Hilt 컴포넌트의 수명동안 매 요청에 동일 인스턴스를 반환
    // 다음의 경우 viewModel의 수명동안 동일 인스턴스를 반환

    @ViewModelScoped
    @Provides
    fun providesCommunityRepository(
        communityRepositoryImpl: CommunityRepositoryImpl
    ): CommunityRepository = communityRepositoryImpl

    @ViewModelScoped
    @Provides
    fun providesViewingPartyRepository(
        viewingPartyRepositoryImpl: ViewingPartyRepositoryImpl
    ): ViewingPartyRepository = viewingPartyRepositoryImpl

    @Provides
    @ViewModelScoped
    fun provideUpdateTeamInfoRepository(
        updateTeamInfoRepositoryImpl: UpdateTeamInfoRepositoryImpl
    ): UpdateTeamInfoRepository = updateTeamInfoRepositoryImpl
}
