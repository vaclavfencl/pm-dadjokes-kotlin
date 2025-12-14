package com.vfencl.pmdadjokeskotlin.data

import kotlinx.coroutines.flow.Flow

interface SavedJokesStore {
    fun observeAll(): Flow<List<String>>
    fun observeSearch(term: String): Flow<List<String>>

    suspend fun isSaved(text: String): Boolean
    suspend fun save(text: String, source: String)
    suspend fun remove(text: String)
}
