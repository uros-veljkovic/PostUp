package com.urkeev14.myapplication.usecase

import com.urkeev14.myapplication.data.source.local.LocalDataSource
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.ui.UiState
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class CacheAllUseCase<Entity>
@Inject constructor(
    private val localDataSource: LocalDataSource<Entity>,
) {
    /**
     * Caches list of [Entity] in local storage using [LocalDataSource]
     *
     * @param list of [Entity]
     * @return [UiState.Success] if data is persisted successfully, else [UiState.Error]
     */
    operator fun invoke(list: List<Entity>) = flow {
        when (val result = localDataSource.insertAll(list)) {
            is RepositoryResponse.Failure -> emit(UiState.Error(result.throwable))
            is RepositoryResponse.Success -> emit(UiState.Success(true))
        }
    }
}