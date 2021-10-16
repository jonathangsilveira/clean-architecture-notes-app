package com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase

import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.repository.NoteRepository

class SaveNoteUseCase(private val repo: NoteRepository) {

    suspend operator fun invoke(note: Note): Result {
        return when {
            note.title.isEmpty() -> Result.Invalid.Title
            note.content.isEmpty() -> Result.Invalid.Content
            else -> {
                repo.insert(note)
                Result.Success
            }
        }
    }

    sealed class Result {
        object Success: Result()
        sealed class Invalid: Result() {
            object Title: Invalid()
            object Content: Invalid()
        }
    }

}
