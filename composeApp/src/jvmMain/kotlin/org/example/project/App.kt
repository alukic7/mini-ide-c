package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.example.project.ui.DarkColors
import org.example.project.ui.editor.EditorPane
import org.example.project.ui.editor.EditorViewModel
import org.example.project.ui.output.OutputPane
import org.example.project.ui.output.OutputViewModel
import org.example.project.ui.runner.RunnerToolbar
import org.example.project.ui.runner.RunnerViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val editorViewModel = remember { EditorViewModel() }
    val outputViewModel = remember { OutputViewModel() }
    val runnerViewModel = remember { RunnerViewModel(
        outputViewModel = outputViewModel,
        editorViewModel = editorViewModel
    ) }

    MaterialTheme(colorScheme = DarkColors) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(Modifier.fillMaxSize()) {
                RunnerToolbar(
                    runnerViewModel = runnerViewModel,
                    onStart = runnerViewModel::start,
                    onStop = runnerViewModel::stop,
                    onClearError = runnerViewModel::clearError
                )
                EditorPane(
                    editorViewModel = editorViewModel,
                    runnerViewModel = runnerViewModel,
                    modifier = Modifier.fillMaxWidth().weight(2f)
                )
                OutputPane(
                    outputViewModel = outputViewModel,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
            }
        }
    }
}
