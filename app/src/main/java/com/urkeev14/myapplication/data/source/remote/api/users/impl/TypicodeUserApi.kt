package com.urkeev14.myapplication.data.source.remote.api.users.impl

import com.urkeev14.myapplication.data.source.remote.api.users.UsersApi
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TypicodeUserApi : UsersApi {

    @GET("/users/{id}")
    override suspend fun getUser(@Path("id") userId: Int): Response<TypicodeUserDto>
}