package com.vfencl.pmdadjokeskotlin.data

import com.vfencl.pmdadjokeskotlin.data.local.SavedJokeDao
import com.vfencl.pmdadjokeskotlin.data.local.SavedJokeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomSavedJokesStore(
    private val dao: SavedJokeDao
) : SavedJokesStore {

    override fun observeAll(): Flow<List<String>> =
        dao.observeAll().map { list -> list.map { it.text } }

    override fun observeSearch(term: String): Flow<List<String>> =
        dao.observeSearch(term).map { list -> list.map { it.text } }

    override suspend fun isSaved(text: String): Boolean = dao.exists(text)

    override suspend fun save(text: String, source: String) {
        dao.insert(SavedJokeEntity(text = text, source = source))
    }

    override suspend fun remove(text: String) {
        dao.deleteByText(text)
    }
}
