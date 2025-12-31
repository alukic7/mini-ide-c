package org.example.project.ui.editor

import androidx.compose.ui.text.TextRange
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditorViewModel( ): ViewModel() {
    private val _state = MutableStateFlow(EditorState())
    val state: StateFlow<EditorState> = _state

    fun onTextChanged(newText: String) {
        _state.value = _state.value.copy(text = newText)
    }

    fun onSelectionChanged(newSelection: TextRange) {
        _state.value = _state.value.copy(selection = newSelection)
    }

    fun navigateTo(line: Int, column: Int) {
        val text = _state.value.text
        val lines = text.split("\n")

        var offset = 0
        for (i in 0 until (line - 1).coerceAtMost(lines.size)) {
            offset += lines[i].length + 1
        }
        offset += (column - 1).coerceIn(0, lines.getOrNull(line - 1)?.length ?: 0)

        _state.value = _state.value.copy(selection = TextRange(offset))
    }
}