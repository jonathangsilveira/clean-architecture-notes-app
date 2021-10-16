package com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.note

import androidx.compose.ui.focus.FocusState

sealed class NoteEvent {
    data class EnteredTitle(val value: String): NoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): NoteEvent()
    data class EnteredContent(val value: String): NoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): NoteEvent()
    data class ChangeColor(val argb: Int): NoteEvent()
    object Save : NoteEvent()
}
