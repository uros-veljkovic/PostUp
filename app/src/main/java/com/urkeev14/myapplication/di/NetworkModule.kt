package com.urkeev14.myapplication.di

import com.urkeev14.myapplication.BuildConfig
import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.data.source.remote.TypicodePostRemoteSourceImpl
import com.urkeev14.myapplication.data.source.remote.TypicodeUserRemoteDataSourceImpl
import com.urkeev14.myapplication.data.source.remote.api.posts.PostApi
import com.urkeev14.myapplication.data.source.remote.api.posts.impl.TypicodePostApi
import com.urkeev14.myapplication.data.source.remote.api.users.UsersApi
import com.urkeev14.myapplication.data.source.remote.api.users.impl.TypicodeUserApi
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserId
import com.urkeev14.myapplication.utils.network.NetworkActionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named(TYPICODE_POST_API)
    fun providePostApi(retrofit: Retrofit): PostApi = retrofit.create<TypicodePostApi>()

    @Provides
    @Singleton
    @Named(TYPICODE_USER_API)
    fun provideUserApi(retrofit: Retrofit): UsersApi = retrofit.create<TypicodeUserApi>()

    @Provides
    @Singleton
    fun provideRemotePostDataSource(
        networkActionHandler: NetworkActionHandler,
        @Named(TYPICODE_POST_API) postApi: PostApi,
    ): RemoteDataSource<TypicodeUserId, TypicodePostDto> = TypicodePostRemoteSourceImpl(networkActionHandler, postApi)

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        networkActionHandler: NetworkActionHandler,
        @Named(TYPICODE_USER_API) usersApi: UsersApi,
    ): RemoteDataSource<TypicodeUserId, TypicodeUserDto> = TypicodeUserRemoteDataSourceImpl(networkActionHandler, usersApi)

    companion object Api {
        const val TYPICODE_POST_API = "TypicodePostApi"
        const val TYPICODE_USER_API = "TypicodeUserApi"
    }
}
