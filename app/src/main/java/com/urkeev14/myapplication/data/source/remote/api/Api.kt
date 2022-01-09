package com.urkeev14.myapplication.data.source.remote.api

interface Api<Dto> {

    /**
     * Get all [Dto] from API
     *
     * @return list of [Dto]
     */
    suspend fun getAll(): List<Dto>

    /**
     * Get [Dto] by [id]
     *
     * @param id id of [Dto]
     * @return [Dto]
     */
    suspend fun getById(id: String): Dto
}
