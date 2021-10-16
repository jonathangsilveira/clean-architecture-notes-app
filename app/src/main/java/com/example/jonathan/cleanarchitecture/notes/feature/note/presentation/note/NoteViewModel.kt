package com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jonathan.cleanarchitecture.notes.R
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase.NotesUseCases
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase.SaveNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCases: NotesUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _title = mutableStateOf(
        TextFieldState(hint = "Enter some content")
    )
    val title: State<TextFieldState>
        get() = _title

    private val _content = mutableStateOf(
        TextFieldState(hint = "Enter some content")
    )
    val content: State<TextFieldState>
        get() = _content

    private val _color = mutableStateOf(randomColorAsArgb())
    val color: State<Int>
        get() = _color

    // SharedFlow to display the UI event once and not survive to orientation changes.
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent>
        get() = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>(NOTE_ID)
            ?.let { id ->
                if (id > -1) loadNote(id)
            }
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.ChangeColor -> changeColor(event.argb)
            is NoteEvent.ChangeContentFocus -> changeContentFocus(event.focusState)
            is NoteEvent.ChangeTitleFocus -> changeTitleFocus(event.focusState)
            is NoteEvent.EnteredContent -> changeContent(event.value)
            is NoteEvent.EnteredTitle -> changeTitle(event.value)
            NoteEvent.Save -> save()
        }
    }

    private fun randomColorAsArgb(): Int = Note.colors.random().toArgb()

    private fun changeColor(argbColor: Int) {
        _color.value = argbColor
    }

    private fun changeContentFocus(focusState: FocusState) {
        val titleState = _title.value
        _title.value = titleState.copy(
            isHintVisible = !focusState.isFocused && titleState.text.isEmpty()
        )
    }

    private fun changeTitleFocus(focusState: FocusState) {
        val contentState = _content.value
        _content.value = contentState.copy(
            isHintVisible = !focusState.isFocused && contentState.text.isEmpty()
        )
    }

    private fun changeTitle(value: String) {
        _title.value = _title.value.copy(text = value)
    }

    private fun changeContent(value: String) {
        _content.value = _content.value.copy(text = value)
    }

    private fun save() {
        viewModelScope.launch {
            val uiEvent = when (useCases.saveNote(createNote())) {
                SaveNoteUseCase.Result.Invalid.Content -> UiEvent.ShowSnackbar(R.string.empty_content)
                SaveNoteUseCase.Result.Invalid.Title -> UiEvent.ShowSnackbar(R.string.empty_title)
                SaveNoteUseCase.Result.Success -> UiEvent.NoteSaved
            }
            _eventFlow.emit(uiEvent)
        }
    }

    private fun createNote(): Note {
        return Note(
            id = currentNoteId,
            title = _title.value.text,
            content = _content.value.text,
            timestamp = System.currentTimeMillis(),
            color = _color.value
        )
    }

    private fun loadNote(id: Int) {
        viewModelScope.launch {
            useCases.getNote(id)
                ?.also { note ->
                    _title.value = _title.value.copy(
                        text = note.title,
                        isHintVisible = false
                    )
                    _content.value = _content.value.copy(
                        text = note.content,
                        isHintVisible = false
                    )
                    _color.value = note.color
                }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val resId: Int): UiEvent()
        object NoteSaved: UiEvent()
    }

    companion object {
        private const val NOTE_ID = "noteId"
    }

}