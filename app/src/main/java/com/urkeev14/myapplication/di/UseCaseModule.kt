package com.urkeev14.myapplication.di

import com.urkeev14.myapplication.data.source.local.LocalDataSource
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.local.entity.TypicodeUserEntity
import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostId
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserId
import com.urkeev14.myapplication.feature.post.FetchPostDetailsUseCase
import com.urkeev14.myapplication.feature.post.TypicodeUserDataMapper
import com.urkeev14.myapplication.feature.posts.TypicodePostDataMapper
import com.urkeev14.myapplication.usecase.CacheAllUseCase
import com.urkeev14.myapplication.usecase.FetchUseCase
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

    // TODO: Migrate to interface injection
    @Provides
    @Singleton
    fun provideFetchTypicodePostsUseCase(
        remoteDataSource: RemoteDataSource<TypicodePostId, TypicodePostDto>,
        mapper: TypicodePostDataMapper,
    ): FetchUseCase<TypicodePostId, TypicodePostDto, TypicodePostEntity> = FetchUseCase(remoteDataSource, mapper)

    // TODO: Migrate to interface injection
    @Provides
    @Singleton
    fun provideFetchTypicodeUsersUseCase(
        remoteDataSource: RemoteDataSource<TypicodeUserId, TypicodeUserDto>,
        mapper: TypicodeUserDataMapper,
    ): FetchUseCase<TypicodeUserId, TypicodeUserDto, TypicodeUserEntity> = FetchUseCase(remoteDataSource, mapper)

    @Provides
    @Singleton
    fun provideFetchPostDetailsUseCase(
        fetchPostUseCase: FetchUseCase<TypicodePostId, TypicodePostDto, TypicodePostEntity>,
        fetchUserUseCase: FetchUseCase<TypicodeUserId, TypicodeUserDto, TypicodeUserEntity>,
    ) = FetchPostDetailsUseCase(fetchUserUseCase, fetchPostUseCase)

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