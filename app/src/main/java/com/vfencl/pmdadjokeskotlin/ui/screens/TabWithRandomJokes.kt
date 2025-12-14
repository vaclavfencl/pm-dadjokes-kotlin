package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vfencl.pmdadjokeskotlin.data.NetworkJokesRepository
import com.vfencl.pmdadjokeskotlin.data.remote.ApiClient
import kotlinx.coroutines.launch

private class RandomVmFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = NetworkJokesRepository(ApiClient.dadJokeApi)
        @Suppress("UNCHECKED_CAST")
        return RandomJokeViewModel(repo) as T
    }
}

@Composable
fun TabWithRandomJokes(modifier: Modifier = Modifier) {
    val vm: RandomJokeViewModel = viewModel(factory = RandomVmFactory())
    val state by vm.state.collectAsState()

    val clipboard = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
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
                Button(onClick = vm::next) { Text("GENERATE NEW JOKE") }

                Button(
                    enabled = state.joke != null,
                    onClick = {
                        val joke = state.joke ?: return@Button
                        clipboard.setText(AnnotatedString(joke))
                        scope.launch { snackbarHostState.showSnackbar("Copied") }
                    }
                ) { Text("COPY ICON") }


                Button(onClick = { /* Ulož do paměti */ }) { Text("HVĚZDIČKA ICON") }
            }
        }
    }
}

