package org.example.project.ui.output

import androidx.compose.foundation.background
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.example.project.ui.editor.EditorViewModel

@Composable
fun OutputPane(
    outputViewModel: OutputViewModel,
    editorViewModel: EditorViewModel,
    modifier: Modifier = Modifier
) {
    val outputState by outputViewModel.state.collectAsState()
    val listState = rememberLazyListState()

    val errorRegex = """.*?:(\d+):(\d+): error:.*""".toRegex()


    LaunchedEffect(outputState.lines.size) {
        if (outputState.lines.isNotEmpty()) {
            listState.scrollToItem(outputState.lines.size - 1)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        items(outputState.lines) { line ->
            val match = errorRegex.find(line.text)

            if (line.type == LineType.ERROR && match != null) {
                val (lineNum, colNum) = match.destructured

                ClickableText(
                    text = AnnotatedString(
                        line.text,
                        spanStyle = SpanStyle(
                            color = MaterialTheme.colorScheme.error,
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    onClick = {
                        editorViewModel.navigateTo(lineNum.toInt(), colNum.toInt())
                    },
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                )
            } else {
                Text(
                    text = line.text,
                    color = if (line.type == LineType.ERROR)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

