package umc.everyones.everyoneslckmanage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import umc.everyones.everyoneslckmanage.data.service.CommunityService
import umc.everyones.everyoneslckmanage.data.service.UpdateTeamInfoService
import umc.everyones.everyoneslckmanage.data.service.ViewingPartyService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    private inline fun <reified T> Retrofit.buildService(): T {
        return this.create(T::class.java)
    }

    @Provides
    @Singleton
    fun provideCommunityService(retrofit: Retrofit): CommunityService {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideViewingPartyService(retrofit: Retrofit): ViewingPartyService {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideUpdateTeamInfoService(retrofit: Retrofit): UpdateTeamInfoService {
        return retrofit.buildService()
    }
}