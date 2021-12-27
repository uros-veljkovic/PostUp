package com.urkeev14.myapplication.data.source.local.entity

data class TypicodePostDetailsEntity(
    /**
     * A post posted by [TypicodeUserEntity]
     */
    val post: TypicodePostEntity,

    /**
     * A user who created the [TypicodePostEntity]
     */
    val user: TypicodeUserEntity,
)
