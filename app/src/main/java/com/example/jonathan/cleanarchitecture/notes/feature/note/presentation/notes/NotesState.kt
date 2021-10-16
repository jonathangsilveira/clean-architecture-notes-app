package com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.notes

import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util.NoteOrder
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val order: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
