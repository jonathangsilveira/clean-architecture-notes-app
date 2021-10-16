package com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.note

data class TextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
