package com.vfencl.pmdadjokeskotlin.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedJokeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(joke: SavedJokeEntity): Long

    @Query("SELECT EXISTS(SELECT 1 FROM saved_jokes WHERE text = :text)")
    suspend fun exists(text: String): Boolean

    @Query("DELETE FROM saved_jokes WHERE text = :text")
    suspend fun deleteByText(text: String)

    @Query("SELECT * FROM saved_jokes ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<SavedJokeEntity>>

    @Query("SELECT * FROM saved_jokes WHERE text LIKE '%' || :term || '%' ORDER BY createdAt DESC")
    fun observeSearch(term: String): Flow<List<SavedJokeEntity>>
}
