package com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jonathan.cleanarchitecture.notes.R
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.notes.components.NoteItem
import com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.notes.components.OrderSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    onAddClick: () -> Unit = {},
    onNoteClick: (Note) -> Unit = {}
) {
    val state: NotesState by viewModel.state
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_note)
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            NotesActionBar(onSortClick = { viewModel.onEvent(NotesEvents.ToggleSortSection) })
            NotesOrderSection(state, viewModel)
            NoteList(viewModel, scaffoldState, state, scope, onNoteClick)
        }
    }
}

@Composable
private fun NoteList(
    viewModel: NotesViewModel,
    scaffoldState: ScaffoldState,
    state: NotesState,
    scope: CoroutineScope,
    onNoteClick: (Note) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(state.notes) { note ->
            NoteItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNoteClick(note) },
                note = note,
                onDelete = {
                    viewModel.onEvent(NotesEvents.Delete(note))
                    scope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = "Note deleted",
                            actionLabel = "Undo"
                        )
                        if (result == SnackbarResult.ActionPerformed)
                            viewModel.onEvent(NotesEvents.Undo)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun ColumnScope.NotesOrderSection(
    state: NotesState,
    viewModel: NotesViewModel,
) {
    AnimatedVisibility(
        visible = state.isOrderSectionVisible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        OrderSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            order = state.order,
            onOrderChange = { viewModel.onEvent(NotesEvents.Sort(it)) }
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun NotesActionBar(onSortClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.your_notes),
            style = MaterialTheme.typography.h4
        )
        IconButton(onClick = onSortClick) {
            Icon(
                imageVector = Icons.Default.Sort,
                contentDescription = stringResource(id = R.string.sort)
            )
        }
    }
}