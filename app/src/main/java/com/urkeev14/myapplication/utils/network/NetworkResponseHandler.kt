package com.urkeev14.myapplication.utils.network

import retrofit2.Response

/**
 * Executes network action and wraps it's result into [RepositoryResponse]
 *
 * @param DTO data received in response body
 * @param load network action being processed
 * @return [RepositoryResponse.Success] if HTTP response is successful, otherwise [RepositoryResponse.Failure]
 */
suspend fun <DTO> executeNetworkAction(load: suspend () -> Response<DTO>): RepositoryResponse<DTO> {
    return load().let { response ->
        if (response.isSuccessful) {
            RepositoryResponse.Success(response.body()!!)
        } else {
            RepositoryResponse.Failure(Throwable(response.message()))
        }
    }
}

/**
 * Executes database action and wraps it's result into [RepositoryResponse]
 *
 * @param Entity data loaded from database
 * @param load database action being processed
 * @return [RepositoryResponse.Success] if database action is successful, otherwise [RepositoryResponse.Failure]
 */
suspend fun <Entity> executeDatabaseAction(load: suspend () -> Entity): RepositoryResponse<Entity> {
    return try {
        val result = load()
        RepositoryResponse.Success(result)
    } catch (e: Exception) {
        RepositoryResponse.Failure(Throwable(e))
    }
}