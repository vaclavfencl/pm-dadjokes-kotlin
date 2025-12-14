package com.vfencl.pmdadjokeskotlin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedJokeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedJokeDao(): SavedJokeDao
}
