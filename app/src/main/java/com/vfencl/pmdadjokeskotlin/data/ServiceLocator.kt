package com.vfencl.pmdadjokeskotlin.data

import android.content.Context
import androidx.room.Room
import com.vfencl.pmdadjokeskotlin.data.local.AppDatabase

object ServiceLocator {

    lateinit var savedStore: SavedJokesStore
        private set

    fun init(context: Context) {
        if (::savedStore.isInitialized) return

        val db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "jokes.db"
        ).build()

        savedStore = RoomSavedJokesStore(db.savedJokeDao())
    }
}
