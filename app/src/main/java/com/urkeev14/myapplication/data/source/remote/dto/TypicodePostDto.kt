package com.urkeev14.myapplication.data.source.remote.dto


data class TypicodePostDto(
    /**
     * Identifier of a post
     */
    val id: Int,

    /**
     * Reference to the user that created the post
     */
    val userId: Int,

    /**
     * Title of a post
     */
    val title: String,

    /**
     * Content of a post
     */
    val body: String,
)