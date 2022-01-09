package com.urkeev14.myapplication.data.source.remote

import com.urkeev14.myapplication.data.source.remote.api.Api
import com.urkeev14.myapplication.utils.network.NetworkActionHandler
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl<Dto>
@Inject constructor(
    private val networkActionHandler: NetworkActionHandler,
    private val api: Api<Dto>,
) : RemoteDataSource<Dto> {

    override suspend fun getAll(): RepositoryResponse<List<Dto>> = withContext(IO) {
        networkActionHandler.execute { api.getAll() }
    }

    override suspend fun getOne(id: String): RepositoryResponse<Dto> = withContext(IO) {
        networkActionHandler.execute { api.getById(id) }
    }
}
