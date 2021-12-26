package com.urkeev14.myapplication.utils.network

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