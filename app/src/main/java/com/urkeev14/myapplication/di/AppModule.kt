package com.urkeev14.myapplication.di

import android.content.Context
import com.urkeev14.myapplication.utils.network.NetworkActionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNetworkActionHandler(@ApplicationContext context: Context) = NetworkActionHandler(context)
}