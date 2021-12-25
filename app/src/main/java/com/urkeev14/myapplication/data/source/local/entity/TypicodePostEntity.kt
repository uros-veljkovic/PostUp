package com.urkeev14.myapplication.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class TypicodePostEntity(
    /**
     * Identifier of a post
     */
    @PrimaryKey val id: Int,

    /**
     * Reference to the user that created a post
     */
    @ColumnInfo(name = "user_id") val userId: Int,

    /**
     * Title of the post
     */
    val title: String,

    /**
     * Content of a post
     */
    val content: String,
)