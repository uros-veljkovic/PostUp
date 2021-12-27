package com.urkeev14.myapplication.data.source.local

import com.urkeev14.myapplication.utils.network.RepositoryResponse
import kotlinx.coroutines.flow.Flow

interface LocalDataSource<Entity> {
    /**
     * Returns list of [Entity] from local data source
     *
     * @return list of [Entity]
     */
    fun getAll(): Flow<RepositoryResponse<List<Entity>>>

    /**
     * Caches list of [Entity]
     *
     * @param list of [Entity] to cache
     * @return [RepositoryResponse.Success] if list is caches successfully, else [RepositoryResponse.Failure]
     */
    suspend fun insertAll(list: List<Entity>): RepositoryResponse<Unit>

    /**
     * Deletes entity from database
     *
     * @param entity entity to delete
     * @return [RepositoryResponse.Success] if list is caches successfully, else [RepositoryResponse.Failure]
     */
    suspend fun delete(entity: Entity): RepositoryResponse<Int>

    /**
     * Deletes all entities from database
     *
     * @return [RepositoryResponse.Success] if database entities are deleted, else [RepositoryResponse.Failure]
     */
    suspend fun deleteAll(): RepositoryResponse<Int>
}