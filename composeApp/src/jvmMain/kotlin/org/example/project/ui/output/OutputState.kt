package org.example.project.ui.output

data class OutputState(
    val lines: List<OutputLine> = emptyList()
)

data class OutputLine(val text: String, val type: LineType)

enum class LineType {
    TEXT,
    ERROR,
}