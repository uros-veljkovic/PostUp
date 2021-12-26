package com.urkeev14.myapplication.usecase

import com.urkeev14.myapplication.data.source.local.LocalDataSource
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.ui.UiState
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class GetAllUseCase<Entity>
@Inject constructor(
    private val localDataSource: LocalDataSource<Entity>,
) {
    /**
     * Returns all instances of type [Entity] from local database.
     *
     * @return [UiState.Success] if data is successfully loaded from database, else [UiState.Error]
     */
    operator fun invoke() = flow {
        emit(UiState.Loading(emptyList()))
        when (val result = localDataSource.getAll()) {
            is RepositoryResponse.Failure -> emit(UiState.Error(result.throwable))
            is RepositoryResponse.Success -> emit(UiState.Success(result.data))
        }
    }
}