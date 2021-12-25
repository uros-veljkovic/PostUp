package com.urkeev14.myapplication.data.source.remote.api.posts

import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {

    /**
     * Get all posts from API
     *
     * @return list of [TypicodePostDto]
     */
    suspend fun getPosts(): Response<List<TypicodePostDto>>
}