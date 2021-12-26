package com.urkeev14.myapplication.data.source.remote

import com.urkeev14.myapplication.data.source.remote.api.posts.PostApi
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostId
import com.urkeev14.myapplication.utils.network.NetworkActionHandler
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import javax.inject.Inject

class TypicodePostRemoteSourceImpl
@Inject constructor(
    private val networkActionHandler: NetworkActionHandler,
    private val postApi: PostApi,
) : RemoteDataSource<TypicodePostId, TypicodePostDto> {

    override suspend fun getAll(): RepositoryResponse<List<TypicodePostDto>> {
        return networkActionHandler.execute { postApi.getPosts() }
    }

    override suspend fun getOne(id: TypicodePostId): RepositoryResponse<TypicodePostDto> {
        return networkActionHandler.execute { postApi.getPost(id) }
    }

}