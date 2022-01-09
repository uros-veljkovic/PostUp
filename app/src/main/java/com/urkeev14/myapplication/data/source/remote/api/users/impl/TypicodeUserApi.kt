package com.urkeev14.myapplication.data.source.remote.api.users.impl

import com.urkeev14.myapplication.data.source.remote.api.Api
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class TypicodeUserApi
@Inject constructor(
    private val client: HttpClient,
) : Api<TypicodeUserDto> {

    override suspend fun getById(id: String): TypicodeUserDto {
        return client.get("/users/$id")
    }

    override suspend fun getAll(): List<TypicodeUserDto> {
        return client.get("/users")
    }
}
