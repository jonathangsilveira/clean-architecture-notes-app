package com.example.jonathan.cleanarchitecture.notes.feature.note.domain.repository

import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insert(note: Note)
    suspend fun delete(note: Note)
}