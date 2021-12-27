package com.urkeev14.myapplication.usecase

import com.urkeev14.myapplication.data.source.remote.RemoteDataSource
import com.urkeev14.myapplication.utils.DataMapper
import com.urkeev14.myapplication.utils.network.RepositoryResponse
import com.urkeev14.myapplication.utils.ui.UiState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchOneUseCase<DtoId, Dto, Entity>
@Inject constructor(
    private val remoteDataSource: RemoteDataSource<DtoId, Dto>,
    private val mapper: DataMapper<Dto, Entity>,
) {

    /**
     * Fetch single [Dto] from [RemoteDataSource] and return it as [Entity]
     *
     * @param id id of [Dto]
     * @return [UiState.Success] if data is successfully fetched, else [UiState.Error]
     */
    operator fun invoke(id: DtoId) = flow {
        emit(UiState.Loading())
        when (val response = remoteDataSource.getOne(id)) {
            is RepositoryResponse.Failure -> emit(UiState.Error(response.throwable))
            is RepositoryResponse.Success -> emit(UiState.Success(mapper.map(response.data)))
        }
    }
}
