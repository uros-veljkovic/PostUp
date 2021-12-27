package com.urkeev14.myapplication.data.source.local

import com.urkeev14.myapplication.data.source.local.database.TypicodePostDatabase
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.network.executeDatabaseAction
import com.urkeev14.myapplication.utils.network.executeFlowableDatabaseAction
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TypicodePostsLocalDataSourceImpl
@Inject constructor(
    private val database: TypicodePostDatabase,
) : LocalDataSource<TypicodePostEntity> {

    override fun getAll(): Flow<RepositoryResponse<List<TypicodePostEntity>>> = executeFlowableDatabaseAction {
        database.dao().getAll()
    }


    override suspend fun insertAll(list: List<TypicodePostEntity>): RepositoryResponse<Unit> = executeDatabaseAction {
        database.dao().insertAll(*list.toTypedArray())
    }

    override suspend fun delete(entity: TypicodePostEntity): RepositoryResponse<Int> = executeDatabaseAction {
        database.dao().delete(entity)
    }


    override suspend fun deleteAll(): RepositoryResponse<Int> = executeDatabaseAction {
        database.dao().deleteAll()
    }
}