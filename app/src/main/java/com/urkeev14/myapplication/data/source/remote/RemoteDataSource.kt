package com.urkeev14.myapplication.data.source.remote

import com.urkeev14.myapplication.utils.network.RepositoryResponse

interface RemoteDataSource<Id, Dto> {
    /**
     * Get list of [Dto] from remote data source
     *
     * @return list of [Dto]
     */
    suspend fun getAll(): RepositoryResponse<List<Dto>>

    /**
     * Get a single [Dto] from remote data source
     *
     * @param id id of [Dto]
     * @return single [Dto]
     */
    suspend fun getOne(id: Id): RepositoryResponse<Dto>
}
