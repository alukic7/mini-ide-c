package org.example.project.ui.output

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OutputViewModel(): ViewModel() {
    val _state = MutableStateFlow(OutputState())
    val state: StateFlow<OutputState> = _state

    fun append(line: String, type: LineType?) {
        val newLine = OutputLine(line, type ?: LineType.TEXT)
        _state.value = _state.value.copy(lines = state.value.lines + newLine)
    }

    fun clear() {
        _state.value = _state.value.copy(lines = emptyList())
    }
}