package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vfencl.pmdadjokeskotlin.data.NetworkJokesRepository
import com.vfencl.pmdadjokeskotlin.data.ServiceLocator
import com.vfencl.pmdadjokeskotlin.data.remote.ApiClient
import kotlinx.coroutines.launch

private class RandomVmFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = NetworkJokesRepository(ApiClient.dadJokeApi)
        val store = ServiceLocator.savedStore
        @Suppress("UNCHECKED_CAST")
        return RandomJokeViewModel(repo, store) as T
    }
}

@Composable
fun TabWithRandomJokes(modifier: Modifier = Modifier) {
    val vm: RandomJokeViewModel = viewModel(factory = RandomVmFactory())
    val state by vm.state.collectAsState()

    val clipboard = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { inner ->
        Column(
            modifier = modifier
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Random Dad Joke", style = MaterialTheme.typography.titleLarge)

            when {
                state.loading -> CircularProgressIndicator()
                state.error != null -> Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                state.joke != null -> Text(state.joke!!, style = MaterialTheme.typography.bodyLarge)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = vm::next, enabled = !state.loading) { Text("GENERATE NEW JOKE") }

                Button(
                    enabled = state.joke != null && !state.loading,
                    onClick = {
                        val joke = state.joke ?: return@Button
                        clipboard.setText(AnnotatedString(joke))
                        scope.launch { snackbarHostState.showSnackbar("Copied") }
                    }
                ) { Text("COPY") }

                Button(
                    enabled = state.joke != null && !state.loading,
                    onClick = {
                        val wasSaved = state.isSaved
                        vm.toggleSaved(source = "API")

                        scope.launch {
                            val res = snackbarHostState.showSnackbar(
                                message = if (wasSaved) "Removed from saved" else "Saved",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short
                            )
                            if (res == SnackbarResult.ActionPerformed) {
                                vm.toggleSaved(source = "API") // Undo = toggle zpět
                            }
                        }
                    }
                ) {
                    Text(if (state.isSaved) "REMOVE" else "SAVE")
                }
            }
        }
    }
}
