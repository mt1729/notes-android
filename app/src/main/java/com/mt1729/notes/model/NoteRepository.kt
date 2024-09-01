package com.mt1729.notes.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

// todo: local persistence DI module / Room
class NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(tmpNotes)
    fun getNotes(): Flow<List<Note>> = _notes

    fun updateNote(note: Note) {
        val noteIndex = _notes.value.indexOfFirst { it.id == note.id }.takeIf { it >= 0 } ?: return
        _notes.update {
            it.toMutableList().apply {
                set(noteIndex, note)
            }
        }
    }

    fun deleteNote(note: Note) {}
    fun addNote(note: Note) {}
}
