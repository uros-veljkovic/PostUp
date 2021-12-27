package com.urkeev14.myapplication.data.source.remote

import com.urkeev14.myapplication.data.source.remote.api.users.UsersApi
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserId
import com.urkeev14.myapplication.utils.network.NetworkActionHandler
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class TypicodeUserRemoteDataSourceImpl @Inject constructor(
    private val networkActionHandler: NetworkActionHandler,
    private val api: UsersApi,
) : RemoteDataSource<TypicodeUserId, TypicodeUserDto> {

    override suspend fun getAll(): RepositoryResponse<List<TypicodeUserDto>> {
        return networkActionHandler.execute { Response.error(400, ResponseBody.create(null, "")) }
    }

    override suspend fun getOne(id: TypicodeUserId): RepositoryResponse<TypicodeUserDto> {
        return networkActionHandler.execute { api.getUser(id) }
    }
}
