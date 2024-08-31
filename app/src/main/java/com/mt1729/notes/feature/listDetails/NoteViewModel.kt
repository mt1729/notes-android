package com.mt1729.notes.feature.listDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mt1729.notes.model.Note
import com.mt1729.notes.model.NoteRepository
import com.mt1729.notes.model.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NoteViewModel {
    val notes: StateFlow<List<Note>>
    val tags: StateFlow<List<Tag>>
    val selectedNote: StateFlow<Note?>

    fun addTagToSelectedNote(tag: Tag) {}
    fun removeTagFromSelectedNote(tag: Tag) {}
    fun deleteNote(note: Note) {}
    fun addNote(note: Note) {}
    fun selectNote(noteIndex: Int) {}
}

// todo: populate VM fields from default state
data class NoteTaggingState(
    val notes: List<Note> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val selectedNote: Note? = null
)

@HiltViewModel
class NoteTaggingViewModel @Inject constructor(
    private val notesRepository: NoteRepository
) : ViewModel(), NoteViewModel {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    override val notes get() = _notes.asStateFlow()

    private val _tags = MutableStateFlow<List<Tag>>(emptyList())
    override val tags get() = _tags.asStateFlow()

    private val _selectedNote = MutableStateFlow<Note?>(null)
    override val selectedNote get() = _selectedNote.asStateFlow()

    init {
        viewModelScope.launch {
            notesRepository.getNotes().collect { newNotes ->
                _notes.update { newNotes }
                _selectedNote.update { newNotes.firstOrNull() }
            }
        }
    }

    override fun selectNote(noteIndex: Int) {
        _selectedNote.update { _notes.value.getOrNull(noteIndex) }
    }

    override fun addTagToSelectedNote(tag: Tag) {
        val note = selectedNote.value ?: return
        notesRepository.updateNote(note)
    }

    override fun removeTagFromSelectedNote(tag: Tag) {
        val note = selectedNote.value ?: return
        notesRepository.updateNote(note)
    }

    override fun deleteNote(note: Note) {
        notesRepository.deleteNote(note)
    }

    override fun addNote(note: Note) {
        notesRepository.addNote(note)
    }
}
