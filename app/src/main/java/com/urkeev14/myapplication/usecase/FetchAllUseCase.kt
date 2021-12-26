package com.urkeev14.myapplication.usecase

import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.DataMapper
import com.urkeev14.myapplication.utils.ui.UiState
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class FetchAllUseCase<Dto, Entity>
@Inject constructor(
    private val remoteDataSource: RemoteDataSource<Dto>,
    private val mapper: DataMapper<Dto, Entity>,
) {
    /**
     * Fetch all [Dto] from [RemoteDataSource] and return them as list of [Entity]
     *
     * @return [UiState.Success] if data is successfully fetched, else [UiState.Error]
     */
    operator fun invoke() = flow {
        emit(UiState.Loading())
        when (val response = remoteDataSource.getAll()) {
            is RepositoryResponse.Failure -> emit(UiState.Error(throwable = response.throwable))
            is RepositoryResponse.Success -> emit(UiState.Success(mapper.map(response.data)))
        }
    }
}