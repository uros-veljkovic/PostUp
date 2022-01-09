package com.urkeev14.myapplication.di

import com.urkeev14.myapplication.data.source.local.LocalDataSource
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.local.entity.TypicodeUserEntity
import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import com.urkeev14.myapplication.feature.post.FetchPostDetailsUseCase
import com.urkeev14.myapplication.feature.post.TypicodeUserDataMapper
import com.urkeev14.myapplication.feature.posts.TypicodePostDataMapper
import com.urkeev14.myapplication.usecase.CacheAllUseCase
import com.urkeev14.myapplication.usecase.FetchAndCacheUseCase
import com.urkeev14.myapplication.usecase.FetchOneUseCase
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
    fun provideTypicodePostDataMapper() = TypicodePostDataMapper()

    @Provides
    @Singleton
    fun provideTypicodeUserDataMapper() = TypicodeUserDataMapper()

    @Provides
    @Singleton
    fun provideFetchTypicodePostsUseCase(
        remoteDataSource: RemoteDataSource<TypicodePostDto>,
        mapper: TypicodePostDataMapper,
    ): FetchOneUseCase<TypicodePostDto, TypicodePostEntity> = FetchOneUseCase(remoteDataSource, mapper)

    @Provides
    @Singleton
    fun provideFetchTypicodeUsersUseCase(
        remoteDataSource: RemoteDataSource<TypicodeUserDto>,
        mapper: TypicodeUserDataMapper,
    ): FetchOneUseCase<TypicodeUserDto, TypicodeUserEntity> = FetchOneUseCase(remoteDataSource, mapper)

    @Provides
    @Singleton
    fun provideFetchPostDetailsUseCase(
        fetchPostUseCase: FetchOneUseCase<TypicodePostDto, TypicodePostEntity>,
        fetchUserUseCase: FetchOneUseCase<TypicodeUserDto, TypicodeUserEntity>,
    ) = FetchPostDetailsUseCase(fetchUserUseCase, fetchPostUseCase)

    @Provides
    @Singleton
    fun provideFetchAndCachePostsUseCase(
        remoteDataSource: RemoteDataSource<TypicodePostDto>,
        localDataSource: LocalDataSource<TypicodePostEntity>,
        mapper: TypicodePostDataMapper,
    ) = FetchAndCacheUseCase(remoteDataSource, localDataSource, mapper)

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
        const val TYPICODE_POST_MAPPER = "TypicodePostDataMapper"
    }
}
