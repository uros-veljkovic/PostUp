package com.urkeev14.myapplication.usecase

import com.urkeev14.myapplication.data.source.local.LocalDataSource
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.network.executeDatabaseAction
import com.urkeev14.myapplication.utils.ui.UiState
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class DeleteUseCase<Entity>
@Inject constructor(
    private val localDataSource: LocalDataSource<Entity>,
) {
    /**
     * Deletes a single [entity] from database table
     *
     * @param entity entity being deleted
     * @return [UiState.Success] if entity is deleted, else [UiState.Error]
     */
    operator fun invoke(entity: Entity) = flow {
        when (executeDatabaseAction { localDataSource.delete(entity) }) {
            is RepositoryResponse.Failure -> emit(UiState.Error())
            is RepositoryResponse.Success -> emit(UiState.Success(Unit))
        }
    }

    /**
     * Deletes all the [Entity]'s from database table
     */
    suspend operator fun invoke() {
        executeDatabaseAction { localDataSource.deleteAll() }
    }
}