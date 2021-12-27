package com.urkeev14.myapplication.data.source.local.entity

data class TypicodeUserEntity(
    /**
     * Identifier of the user
     */
    val id: Int,

    /**
     * User's full name
     */
    val fullName: String,

    /**
     * User's email
     */
    val email: String,
)