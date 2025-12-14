package com.vfencl.pmdadjokeskotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vfencl.pmdadjokeskotlin.data.ServiceLocator
import kotlinx.coroutines.launch

@Composable
fun TabWithCustomJokes(modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

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
            Text("Vlastní vtip", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Napiš vtip") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )

            val canSave = text.trim().isNotEmpty() && !isSaving

            Button(
                enabled = canSave,
                onClick = {
                    val savedText = text.trim()

                    scope.launch {
                        isSaving = true
                        try {
                            ServiceLocator.savedStore.save(savedText, source = "CUSTOM")
                            text = "" // smaž až po úspěšném uložení

                            val res = snackbarHostState.showSnackbar(
                                message = "Uloženo",
                                actionLabel = "Zrušit",
                                duration = SnackbarDuration.Short
                            )
                            if (res == SnackbarResult.ActionPerformed) {
                                ServiceLocator.savedStore.remove(savedText)
                                snackbarHostState.showSnackbar("Vráceno")
                            }
                        } catch (t: Throwable) {
                            snackbarHostState.showSnackbar("Nepodařilo se uložit")
                        } finally {
                            isSaving = false
                        }
                    }
                }
            ) {
                Text("SAVE")
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
