package com.mt1729.notes.feature.listDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mt1729.notes.model.Note
import com.mt1729.notes.model.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    private val notesRepository: NoteRepository
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes get() = _notes.asStateFlow()

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote get() = _selectedNote.asStateFlow()

    // Does not need to maintain a separate state from the current note (no backing flow)
    val selectedNoteText = _selectedNote.map { currNote ->
        val notes = _notes.value
        val currNoteIndex = notes.indexOf(currNote)

        return@map if (currNote == null || currNoteIndex < 0) {
            ""
        } else {
            "${currNoteIndex + 1} / ${notes.size}"
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    init {
        viewModelScope.launch {
            notesRepository.getNotes().collect { newNotes ->
                _notes.update { newNotes }
                _selectedNote.update { newNotes.firstOrNull() }
            }
        }
    }

    fun selectNote(note: Note) {

    }

    fun addTagToNote() {

    }

    fun removeTagFromNote() {

    }
}
