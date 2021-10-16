package com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase

data class NotesUseCases(
    val getNotes: GetNotesUseCase,
    val saveNote: SaveNoteUseCase,
    val deleteNote: DeleteNoteUseCase,
    val getNote: GetNoteUseCase
)