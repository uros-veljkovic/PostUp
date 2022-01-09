package com.urkeev14.myapplication.di

import com.urkeev14.myapplication.BuildConfig
import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.data.source.remote.RemoteDataSourceImpl
import com.urkeev14.myapplication.data.source.remote.api.posts.impl.TypicodePostApi
import com.urkeev14.myapplication.data.source.remote.api.users.impl.TypicodeUserApi
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import com.urkeev14.myapplication.utils.network.NetworkActionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.URLProtocol
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideKtorClient() = HttpClient(CIO) {
        defaultRequest {
            url {
                host = BuildConfig.BASE_URL
                protocol = URLProtocol.HTTPS
            }
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    @Provides
    @Singleton
    fun providePostApi(client: HttpClient): TypicodePostApi = TypicodePostApi(client)

    @Provides
    @Singleton
    fun provideUserApi(client: HttpClient): TypicodeUserApi = TypicodeUserApi(client)

    @Provides
    @Singleton
    fun provideRemotePostDataSource(
        networkActionHandler: NetworkActionHandler,
        api: TypicodePostApi,
    ): RemoteDataSource<TypicodePostDto> = RemoteDataSourceImpl(networkActionHandler, api)

    @Provides
    @Singleton
    fun provideRemoteUserDataSource(
        networkActionHandler: NetworkActionHandler,
        api: TypicodeUserApi,
    ): RemoteDataSource<TypicodeUserDto> = RemoteDataSourceImpl(networkActionHandler, api)
}
