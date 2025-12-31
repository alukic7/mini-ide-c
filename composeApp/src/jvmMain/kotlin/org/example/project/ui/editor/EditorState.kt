package org.example.project.ui.editor

import androidx.compose.ui.text.TextRange

data class EditorState(
    val text: String = "",
    val selection: TextRange = TextRange.Zero
)

