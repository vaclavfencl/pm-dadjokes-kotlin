package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfencl.pmdadjokeskotlin.data.JokesRepository
import com.vfencl.pmdadjokeskotlin.data.SavedJokesStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RandomUiState(
    val loading: Boolean = false,
    val joke: String? = null,
    val isSaved: Boolean = false,
    val error: String? = null
)

class RandomJokeViewModel(
    private val repo: JokesRepository,
    private val savedStore: SavedJokesStore
) : ViewModel() {

    private val _state = MutableStateFlow(RandomUiState(loading = true))
    val state: StateFlow<RandomUiState> = _state

    init { next() }

    fun next() {
        viewModelScope.launch {
            _state.value = RandomUiState(loading = true)
            try {
                val text = repo.getRandomJokeText()
                val saved = savedStore.isSaved(text)
                _state.value = RandomUiState(loading = false, joke = text, isSaved = saved)
            } catch (t: Throwable) {
                _state.value = RandomUiState(loading = false, error = t.message ?: "Network error")
            }
        }
    }

    fun toggleSaved(source: String = "API") {
        val text = _state.value.joke ?: return
        viewModelScope.launch {
            val currentlySaved = savedStore.isSaved(text)
            if (currentlySaved) savedStore.remove(text)
            else savedStore.save(text, source)

            _state.value = _state.value.copy(isSaved = !currentlySaved)
        }
    }
}
