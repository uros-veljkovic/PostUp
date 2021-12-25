package com.urkeev14.myapplication.di

import android.content.Context
import androidx.room.Room
import com.urkeev14.myapplication.data.source.local.LocalDataSource
import com.urkeev14.myapplication.data.source.local.TypicodePostsLocalDataSourceImpl
import com.urkeev14.myapplication.data.source.local.database.TypicodePostDatabase
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providePostDatabase(@ApplicationContext context: Context): TypicodePostDatabase = Room.databaseBuilder(
        context,
        TypicodePostDatabase::class.java,
        "posts"
    ).build()

    @Provides
    @Singleton
    fun provideLocalPostDataSource(
        postDatabase: TypicodePostDatabase,
    ): LocalDataSource<TypicodePostEntity> = TypicodePostsLocalDataSourceImpl(postDatabase)
}