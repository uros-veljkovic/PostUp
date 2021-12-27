package com.urkeev14.myapplication.utils.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Handles database method that return [Flow] of [Entity] and wraps [Entity] with [RepositoryResponse]
 *
 * @param Entity data loaded from database
 * @param load flowable database action being processed
 * @return [Flow] of [RepositoryResponse.Success] if database action is successful, otherwise [Flow] of [RepositoryResponse.Failure]
 */
fun <Entity> executeFlowableDatabaseAction(load: () -> Flow<Entity>): Flow<RepositoryResponse<Entity>> {
    return load().map {
        RepositoryResponse.Success(it)
    }.catch { exception ->
        RepositoryResponse.Failure<RepositoryResponse<Entity>>(Throwable(exception.message))
    }
}

suspend fun <Entity> executeDatabaseAction(load: suspend () -> Entity): RepositoryResponse<Entity> {
    return try {
        val result = load()
        RepositoryResponse.Success(result)
    } catch (e: Exception) {
        RepositoryResponse.Failure(Throwable(e))
    }
}
