package com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase.SaveNoteUseCase
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase.NotesUseCases
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util.NoteOrder
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val useCases: NotesUseCases): ViewModel() {

    private var recentlyDeletedNote: Note? = null

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState>
        get() = _state

    private var sortNotesJob: Job? = null

    init {
        sortNotesBy(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvents) {
        when (event) {
            is NotesEvents.Delete -> delete(event.note)
            NotesEvents.Undo -> undo()
            is NotesEvents.Sort -> sortNotesBy(event.order)
            NotesEvents.ToggleSortSection -> toggleSortSection()
        }
    }

    private fun delete(note: Note) {
        viewModelScope.launch {
            useCases.deleteNote(note)
            recentlyDeletedNote = note
        }
    }

    private fun undo() {
        val note = recentlyDeletedNote ?: return
        viewModelScope.launch {
            when (useCases.saveNote(note)) {
                SaveNoteUseCase.Result.Invalid.Content -> TODO()
                SaveNoteUseCase.Result.Invalid.Title -> TODO()
                SaveNoteUseCase.Result.Success -> recentlyDeletedNote = null
            }
        }
    }

    private fun toggleSortSection() {
        val stateValue = _state.value
        _state.value = stateValue.copy(isOrderSectionVisible = !stateValue.isOrderSectionVisible)
    }

    private fun sortNotesBy(order: NoteOrder) {
        sortNotesJob?.cancel()
        sortNotesJob = useCases.getNotes(order)
            .onEach {
                _state.value = _state.value.copy(notes = it, order = order)
            }
            .launchIn(viewModelScope)
    }

}