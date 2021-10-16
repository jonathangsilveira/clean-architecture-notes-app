package com.example.jonathan.cleanarchitecture.notes.feature.note.data.repository

import com.example.jonathan.cleanarchitecture.notes.feature.note.data.datasource.NoteDao
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository {
    override fun getNotes(): Flow<List<Note>> = dao.getNotes()
    override suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)
    override suspend fun insert(note: Note) = dao.insertOrUpdate(note)
    override suspend fun delete(note: Note) = dao.delete(note)
}