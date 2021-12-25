package com.urkeev14.myapplication.data.source.remote

import com.urkeev14.myapplication.utils.network.RepositoryResponse

interface RemoteDataSource<Dto> {
    /**
     * Get list of [Dto] from remote data source
     *
     * @return list of [Dto]
     */
    suspend fun getAll(): RepositoryResponse<List<Dto>>
}
