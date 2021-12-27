package com.urkeev14.myapplication.utils.network

sealed class RepositoryResponse<T> {

    /**
     * Represents successful result of either [LocalDataSource] or [RemoteDataSource] operation.
     *
     * @param U type of result that has been processed by data source.
     * @property data data being wrapped for further processing.
     */
    class Success<U>(val data: U) : RepositoryResponse<U>()

    /**
     * Represents successful result of either [LocalDataSource] or [RemoteDataSource] operation.
     *
     * @param V type of result that has been processed by data source.
     * @property throwable that is wrapped for further processing.
     */
    class Failure<V>(val throwable: Throwable) : RepositoryResponse<V>()
}
