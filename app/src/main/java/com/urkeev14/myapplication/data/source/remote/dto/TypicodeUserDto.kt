package com.urkeev14.myapplication.data.source.remote.dto

typealias TypicodeUserId = Int

data class TypicodeUserDto(
    /**
     * Identifier of a user
     */
    val id: TypicodeUserId,

    /**
     * User's full name
     */
    val name: String,

    /**
     * User's email
     */
    val email: String,
)