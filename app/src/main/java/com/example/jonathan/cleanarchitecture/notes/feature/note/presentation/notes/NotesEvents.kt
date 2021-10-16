package com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.notes

import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util.NoteOrder

sealed class NotesEvents {
    data class Sort(val order: NoteOrder): NotesEvents()
    data class Delete(val note: Note): NotesEvents()
    object Undo: NotesEvents()
    object ToggleSortSection: NotesEvents()
}
