package com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase

data class NotesUseCases(
    val getNotes: GetNotesUseCase,
    val restoreNoteUseCase: InsertNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase
)