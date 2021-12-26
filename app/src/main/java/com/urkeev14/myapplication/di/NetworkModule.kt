package com.urkeev14.myapplication.di

import com.urkeev14.myapplication.BuildConfig
import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.data.source.remote.TypicodePostRemoteSourceImpl
import com.urkeev14.myapplication.data.source.remote.api.posts.PostApi
import com.urkeev14.myapplication.data.source.remote.api.posts.impl.TypicodePostApi
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.utils.network.NetworkActionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

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
    @Named(TYPICODE)
    fun providePostApi(retrofit: Retrofit): PostApi = retrofit.create<TypicodePostApi>()

    @Provides
    @Singleton
    fun provideRemotePostDataSource(
        networkActionHandler: NetworkActionHandler,
        @Named(TYPICODE) postApi: PostApi,
    ): RemoteDataSource<TypicodePostDto> = TypicodePostRemoteSourceImpl(networkActionHandler, postApi)

    companion object Api {
        const val TYPICODE = "TypicodePostApi"
    }

}