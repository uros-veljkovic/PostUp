package com.urkeev14.myapplication.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity

@Dao
interface TypicodePostDao {

    @Query("SELECT * FROM post")
    fun getAll(): List<TypicodePostEntity>

    @Insert
    fun insertAll(list: List<TypicodePostEntity>): List<Long>

    @Delete
    fun delete(entity: TypicodePostEntity): Int
}