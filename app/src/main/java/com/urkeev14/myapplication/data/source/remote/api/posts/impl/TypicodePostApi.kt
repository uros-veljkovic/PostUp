package com.urkeev14.myapplication.data.source.remote.api.posts.impl

import com.urkeev14.myapplication.data.source.remote.api.posts.PostApi
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TypicodePostApi : PostApi {

    @GET("/posts")
    override suspend fun getPosts(): Response<List<TypicodePostDto>>

    @GET("/posts/{id}")
    override suspend fun getPost(@Path("id") id: Int): Response<TypicodePostDto>
}
