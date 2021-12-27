package com.urkeev14.myapplication.data.source.remote.api.posts

import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import retrofit2.Response

interface PostApi {

    /**
     * Get all posts from API
     *
     * @return list of [TypicodePostDto]
     */
    suspend fun getPosts(): Response<List<TypicodePostDto>>

    /**
     * Get post by [id]
     *
     * @param id id of [TypicodePostDto]
     * @return [TypicodePostDto]
     */
    suspend fun getPost(id: Int): Response<TypicodePostDto>
}
