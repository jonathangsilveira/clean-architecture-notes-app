package com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase

import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repo: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        repo.delete(note)
    }

}