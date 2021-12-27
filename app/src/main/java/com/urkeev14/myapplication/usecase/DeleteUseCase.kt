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
    operator fun invoke(entity: Entity) = flow {
        emit(UiState.Loading())
        when (val result = executeDatabaseAction { localDataSource.delete(entity) }){
            is RepositoryResponse.Failure -> emit(UiState.Success(false))
            is RepositoryResponse.Success -> emit(UiState.Success(true))
        }
    }
}