package com.urkeev14.myapplication.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.urkeev14.myapplication.data.source.local.dao.TypicodePostDao
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity

@Database(entities = [TypicodePostEntity::class], version = 1)
abstract class TypicodePostDatabase : RoomDatabase() {
    abstract fun dao(): TypicodePostDao
}