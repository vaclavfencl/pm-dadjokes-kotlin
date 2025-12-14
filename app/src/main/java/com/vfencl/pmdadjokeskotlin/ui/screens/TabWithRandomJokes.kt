package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.outlined.StarBorder

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

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Náhodný dad joke", style = MaterialTheme.typography.titleLarge)

            when {
                state.loading -> CircularProgressIndicator()
                state.error != null -> Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                state.joke != null -> Text(state.joke!!, style = MaterialTheme.typography.bodyLarge)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = vm::next, enabled = !state.loading) { Text("GENERATE NEW JOKE") }

                FilledTonalIconButton(
                    enabled = state.joke != null && !state.loading,
                    onClick = {
                        val joke = state.joke ?: return@FilledTonalIconButton
                        clipboard.setText(AnnotatedString(joke))
                        scope.launch { snackbarHostState.showSnackbar("Zkopírováno") }
                    }
                ) {
                    Icon(Icons.Filled.ContentCopy, contentDescription = "Kopírovat")
                }

                FilledTonalIconButton(
                    enabled = state.joke != null && !state.loading,
                    onClick = {
                        val wasSaved = state.isSaved
                        vm.toggleSaved(source = "API")

                        scope.launch {
                            val res = snackbarHostState.showSnackbar(
                                message = if (wasSaved) "Odebráno z uložených" else "Uloženo",
                                actionLabel = "Zrušit",
                                duration = SnackbarDuration.Short
                            )
                            if (res == SnackbarResult.ActionPerformed) {
                                vm.toggleSaved(source = "API")
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = when {
                            state.isSaved -> Icons.Filled.Undo
                            else -> Icons.Outlined.StarBorder
                        },
                        contentDescription = if (state.isSaved) "Smazat" else "Uložit"
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 88.dp)
        )
    }
}
