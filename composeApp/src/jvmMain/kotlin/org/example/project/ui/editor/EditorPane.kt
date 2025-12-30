package org.example.project.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.example.project.keywordhl.KeywordHighlighter
import org.example.project.ui.runner.RunnerViewModel

@Composable
fun EditorPane(
    editorViewModel: EditorViewModel,
    runnerViewModel: RunnerViewModel,
    modifier: Modifier = Modifier
) {
    val editorState by editorViewModel.state.collectAsState()
    val runnerState by runnerViewModel.state.collectAsState()

    Column(
        modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        BasicTextField(
            value = editorState.text,
            onValueChange = editorViewModel::onTextChanged,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            visualTransformation = KeywordHighlighter.getVisualTransformation(runnerState.runtimeConfiguration),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        )
    }
}
