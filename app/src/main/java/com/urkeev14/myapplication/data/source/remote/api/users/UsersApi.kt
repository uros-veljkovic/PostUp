package com.urkeev14.myapplication.data.source.remote.api.users

import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserDto
import retrofit2.Response

interface UsersApi {

    /**
     * Get user by [userId]
     *
     * @param userId
     * @return
     */
    suspend fun getUser(userId: Int): Response<TypicodeUserDto>
}