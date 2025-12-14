package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vfencl.pmdadjokeskotlin.data.JokesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RandomUiState(
    val loading: Boolean = false,
    val joke: String? = null,
    val error: String? = null
)

class RandomJokeViewModel(
    private val repo: JokesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RandomUiState(loading = true))
    val state: StateFlow<RandomUiState> = _state

    init { next() }

    fun next() {
        viewModelScope.launch {
            _state.value = RandomUiState(loading = true)
            try {
                _state.value = RandomUiState(joke = repo.getRandomJokeText())
            } catch (t: Throwable) {
                _state.value = RandomUiState(error = t.message ?: "Network error")
            }
        }
    }
}
