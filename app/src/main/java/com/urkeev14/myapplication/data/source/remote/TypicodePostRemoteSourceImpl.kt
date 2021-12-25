package com.urkeev14.myapplication.data.source.remote

import com.urkeev14.myapplication.data.source.remote.api.posts.PostApi
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.network.executeNetworkAction
import javax.inject.Inject

class TypicodePostRemoteSourceImpl
@Inject constructor(
    private val postApi: PostApi,
) : RemoteDataSource<TypicodePostDto> {

    override suspend fun getAll(): RepositoryResponse<List<TypicodePostDto>> {
        return executeNetworkAction { postApi.getPosts() }
    }

}