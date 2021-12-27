package com.urkeev14.myapplication.feature.posts

import com.urkeev14.myapplication.data.source.local.LocalDataSource
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostId
import com.urkeev14.myapplication.utils.DataMapper
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.ui.UiState
import javax.inject.Inject

class FetchAndCachePostsUseCase
@Inject constructor(
    private val remoteDataSource: RemoteDataSource<TypicodePostId, TypicodePostDto>,
    private val localDataSource: LocalDataSource<TypicodePostEntity>,
    private val mapper: DataMapper<TypicodePostDto, TypicodePostEntity>,
) {
    /**
     * Fetches a list of [TypicodePostDto], maps it into [TypicodePostEntity] and caches it into database.
     *
     * @return [UiState.Success] if both fetching and caching are successful, else [UiState.Error]
     */
    suspend operator fun invoke(): UiState<Boolean> {
        return when (val response = remoteDataSource.getAll()) {
            is RepositoryResponse.Failure -> UiState.Error(response.throwable)
            is RepositoryResponse.Success -> {
                handlePostsFetch(mapper.map(response.data))
            }
        }
    }

    private suspend fun handlePostsFetch(data: List<TypicodePostEntity>): UiState<Boolean> {
        return when (val result = localDataSource.insertAll(data)) {
            is RepositoryResponse.Failure -> UiState.Error(result.throwable)
            is RepositoryResponse.Success -> UiState.Success(true)
        }
    }
}