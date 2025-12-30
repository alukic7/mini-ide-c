package org.example.project.ui.output

import androidx.compose.foundation.background
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OutputPane(outputViewModel: OutputViewModel, modifier: Modifier = Modifier) {
    val outputState by outputViewModel.state.collectAsState()
    val listState = rememberLazyListState()

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

