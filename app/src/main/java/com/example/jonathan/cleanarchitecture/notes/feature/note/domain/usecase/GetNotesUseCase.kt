package com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase

import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.repository.NoteRepository
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util.NoteOrder
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repo: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repo.getNotes()
            .map { notes ->
                when (noteOrder.type) {
                    OrderType.Ascending -> notes.sortAsc(noteOrder)
                    OrderType.Descending -> notes.sortDesc(noteOrder)
                }
            }
    }

    private fun List<Note>.sortDesc(noteOrder: NoteOrder): List<Note> {
        return when (noteOrder) {
            is NoteOrder.Color -> this.sortedByDescending { it.color }
            is NoteOrder.Date -> this.sortedByDescending { it.timestamp }
            is NoteOrder.Title -> this.sortedByDescending { it.title.lowercase() }
        }
    }

    private fun List<Note>.sortAsc(noteOrder: NoteOrder): List<Note> {
        return when (noteOrder) {
            is NoteOrder.Color -> this.sortedBy { it.color }
            is NoteOrder.Date -> this.sortedBy { it.timestamp }
            is NoteOrder.Title -> this.sortedBy { it.title.lowercase() }
        }
    }

}