package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.vfencl.pmdadjokeskotlin.data.SavedJokesStore
import com.vfencl.pmdadjokeskotlin.data.ServiceLocator
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


private class SavedVmFactory(
    private val store: SavedJokesStore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SavedJokesViewModel(store) as T
    }
}

private class SavedJokesViewModel(
    private val store: SavedJokesStore
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val jokes: StateFlow<List<String>> =
        query
            .debounce(150)
            .distinctUntilChanged()
            .flatMapLatest { term ->
                if (term.isBlank()) store.observeAll()
                else store.observeSearch(term)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onQueryChange(s: String) {
        _query.value = s
    }

    fun remove(joke: String) {
        viewModelScope.launch { store.remove(joke) }
    }
}

@Composable
fun TabWithSavedJokes(modifier: Modifier = Modifier) {
    val store = ServiceLocator.savedStore
    val vm: SavedJokesViewModel = viewModel(factory = SavedVmFactory(store))

    val query by vm.query.collectAsState()
    val jokes by vm.jokes.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Oblíbené vtipy", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = query,
            onValueChange = vm::onQueryChange,
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (jokes.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = if (query.isBlank()) "Nemáte uložené žádné vtipy"
                    else "No matching jokes found.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(jokes, key = { it }) { joke ->
                    Card {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = joke,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            TextButton(onClick = { vm.remove(joke) }) {
                                Text("Odebrat")
                            }
                        }
                    }
                }
            }
        }
    }
}
