package com.urkeev14.myapplication.data.source.local

import com.urkeev14.myapplication.data.source.local.database.TypicodePostDatabase
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.network.executeDatabaseAction
import javax.inject.Inject

class TypicodePostsLocalDataSourceImpl
@Inject constructor(
    private val database: TypicodePostDatabase,
) : LocalDataSource<TypicodePostEntity> {

    override suspend fun getAll(): RepositoryResponse<List<TypicodePostEntity>> {
        return executeDatabaseAction { database.dao().getPosts() }
    }

    override suspend fun insertAll(list: List<TypicodePostEntity>): RepositoryResponse<List<Long>> {
        return executeDatabaseAction { database.dao().insertAll(list) }
    }

}