package com.urkeev14.myapplication.data.source.local

import com.urkeev14.myapplication.utils.network.RepositoryResponse

interface LocalDataSource<Entity> {
    /**
     * Returns list of [Entity] from local data source
     *
     * @return list of [Entity]
     */
    suspend fun getAll(): RepositoryResponse<List<Entity>>

    /**
     * Caches list of [Entity]
     *
     * @param list of [Entity] to cache
     * @return [RepositoryResponse.Success] if list is caches successfully, else [RepositoryResponse.Failure]
     */
    suspend fun insertAll(list: List<Entity>): RepositoryResponse<List<Long>>
}

/**
 * Indicates that a method does not return any value
 */
class NoResponse