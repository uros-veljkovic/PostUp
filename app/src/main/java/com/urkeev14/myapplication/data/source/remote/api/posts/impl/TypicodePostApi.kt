package com.urkeev14.myapplication.data.source.remote.api.posts.impl

import com.urkeev14.myapplication.data.source.remote.api.Api
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class TypicodePostApi
@Inject constructor(
    private val client: HttpClient,
) : Api<TypicodePostDto> {

    override suspend fun getAll(): List<TypicodePostDto> {
        return client.get("/posts")
    }

    override suspend fun getById(id: String): TypicodePostDto {
        return client.get("/posts/$id")
    }
}
