package org.example.project.ui.runner

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun RunnerToolbar(
    runnerViewModel: RunnerViewModel,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onClearError: () -> Unit
) {
    val state by runnerViewModel.state.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Row(
        Modifier.fillMaxWidth().padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onStart,
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
            enabled = !state.isRunning
        ) { Text("Run") }

        OutlinedButton(
            onClick = onStop,
            modifier =
                if(state.isRunning) Modifier.pointerHoverIcon(PointerIcon.Hand)
                else Modifier.pointerHoverIcon(PointerIcon.Default),
            enabled = state.isRunning
        )
        { Text("Stop") }

        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                enabled = !state.isRunning
            ) {
                Text(state.runtimeConfiguration.name)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                RuntimeConfiguration.entries.forEach { config ->
                    DropdownMenuItem(
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                        runnerViewModel.setRuntimeConfiguration(config)
                        expanded = false
                    }) {
                        Text("${config.name} (${config.desc})")
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        val status = if (state.isRunning) "Running" else "Idle"
        Text(
            status,
            color =
                if(state.isRunning) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.primary
        )

        state.lastExitCode?.let { code ->
            Spacer(Modifier.width(12.dp))
            val label = if (code == 0) "Exit: 0" else "Exit: $code"
            Text(
                label,
                color =
                    if (code == 0) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.error
            )
        }
    }

    state.lastErrorMessage?.let { msg ->
        LaunchedEffect(msg) {
            delay(3_000)
            onClearError()
        }

        Text(
            text = "Error: $msg",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}