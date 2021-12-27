package com.urkeev14.myapplication.usecase

import com.urkeev14.myapplication.data.source.local.LocalDataSource
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import com.urkeev14.myapplication.utils.DataMapper
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.ui.UiState
import javax.inject.Inject

class FetchAndCacheUseCase<DtoId, Dto, Entity>
@Inject constructor(
    private val remoteDataSource: RemoteDataSource<DtoId, Dto>,
    private val localDataSource: LocalDataSource<Entity>,
    private val mapper: DataMapper<Dto, Entity>,
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
                handleSuccess(mapper.map(response.data))
            }
        }
    }

    private suspend fun handleSuccess(data: List<Entity>): UiState<Boolean> {
        return when (val result = localDataSource.insertAll(data)) {
            is RepositoryResponse.Failure -> UiState.Error(result.throwable)
            is RepositoryResponse.Success -> UiState.Success(true)
        }
    }
}
