package com.urkeev14.myapplication.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TypicodePostDao {

    @Query("SELECT * FROM post")
    fun getAll(): Flow<List<TypicodePostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg posts: TypicodePostEntity)

    @Delete
    suspend fun delete(entity: TypicodePostEntity): Int

    @Query("DELETE FROM post")
    suspend fun deleteAll(): Int
}