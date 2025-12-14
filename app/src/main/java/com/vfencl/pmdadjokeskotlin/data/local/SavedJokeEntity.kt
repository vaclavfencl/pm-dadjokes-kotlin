package com.vfencl.pmdadjokeskotlin.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "saved_jokes",
    indices = [Index(value = ["text"], unique = true)]
)
data class SavedJokeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val source: String,
    val createdAt: Long = System.currentTimeMillis()
)
