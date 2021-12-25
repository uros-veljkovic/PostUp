package com.urkeev14.myapplication.di

import com.urkeev14.myapplication.data.source.local.LocalDataSource
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.feature.posts.TypicodePostDataMapper
import com.urkeev14.myapplication.usecase.CacheAllUseCase
import com.urkeev14.myapplication.usecase.FetchAllUseCase
import com.urkeev14.myapplication.usecase.GetAllUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideTypicodeMapper() = TypicodePostDataMapper()

    // TODO: Migrate to interface injection
    @Provides
    @Singleton
    fun provideFetchTypicodePostsUseCase(
        remoteDataSource: RemoteDataSource<TypicodePostDto>,
        mapper: TypicodePostDataMapper,
    ): FetchAllUseCase<TypicodePostDto, TypicodePostEntity> = FetchAllUseCase(remoteDataSource, mapper)

    @Provides
    @Singleton
    fun provideGetTypicodePostsUseCase(
        localDataSource: LocalDataSource<TypicodePostEntity>,
    ): GetAllUseCase<TypicodePostEntity> = GetAllUseCase(localDataSource)

    @Provides
    @Singleton
    fun provideCacheTypicodePostsUseCase(
        localDataSource: LocalDataSource<TypicodePostEntity>,
    ): CacheAllUseCase<TypicodePostEntity> = CacheAllUseCase(localDataSource)

    companion object {
        const val TYPICODE_MAPPER = "TypicodePostDataMapper"
    }

}