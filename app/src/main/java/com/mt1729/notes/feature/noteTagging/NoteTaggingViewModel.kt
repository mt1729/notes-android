package com.mt1729.notes.feature.noteTagging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mt1729.notes.model.Note
import com.mt1729.notes.model.NoteRepository
import com.mt1729.notes.model.Tag
import com.mt1729.notes.model.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Currently only for easier Compose previews
interface NoteTaggingViewModelI {
    val tags: StateFlow<List<Tag>>
    val notes: StateFlow<List<Note>>
    val selectedNote: StateFlow<Note?>
    val selectedNoteHeader: StateFlow<String>
    val tagSearchQuery: StateFlow<String>
    val filteredTags: StateFlow<List<Tag>>

    fun addNote(note: Note) {}
    fun deleteNote(note: Note) {}
    fun selectNote(noteIndex: Int) {}
    fun filterTags(query: String) {}
    fun addTagToSelectedNote(tag: Tag) {}
    fun removeTagFromSelectedNote(tag: Tag) {}
    fun addTag(name: String) {}
}

// todo: populate VM fields from default state
data class NoteTaggingState(
    val tags: List<Tag> = emptyList(),
    val notes: List<Note> = emptyList(),
    val selectedNote: Note? = null,
    val searchTagQuery: String = "",
)

@HiltViewModel
class NoteTaggingViewModel @Inject constructor(
    // todo: use cases
    private val noteRepository: NoteRepository,
    private val tagRepository: TagRepository,
) : ViewModel(), NoteTaggingViewModelI {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    override val notes get() = _notes.asStateFlow()

    // todo: refactor to set (list checking redundant)
    private val _tags = MutableStateFlow<List<Tag>>(emptyList())
    override val tags get() = _tags.asStateFlow()

    private val _tagSearchQuery = MutableStateFlow("")
    override val tagSearchQuery get() = _tagSearchQuery.asStateFlow()

    private val _selectedNote = MutableStateFlow<Note?>(null)
    override val selectedNote get() = _selectedNote.asStateFlow()

    override val filteredTags get() = combine(_tags, _tagSearchQuery) { tags, tagSearchQuery ->
        tags.filter {
            it.name.lowercase().startsWith(tagSearchQuery.lowercase())
        }.sortedBy {
            it.name
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    override val selectedNoteHeader = _selectedNote.map { selectedNote ->
        val notes = _notes.value
        val currNoteIndex = notes.indexOfFirst { it.id == selectedNote?.id }

        return@map if (selectedNote == null || currNoteIndex < 0) {
            ""
        } else {
            "${currNoteIndex + 1} / ${notes.size}"
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    init {
        viewModelScope.launch {
            noteRepository.getNotes().collect { newNotes ->
                _notes.update { newNotes }
            }
        }

        viewModelScope.launch {
            tagRepository.getTags().collect { newTags ->
                _tags.update { newTags }
            }
        }
    }

    override fun selectNote(noteIndex: Int) {
        _selectedNote.update { _notes.value.getOrNull(noteIndex) }
    }

    override fun addTagToSelectedNote(tag: Tag) {
        val selectedNote = selectedNote.value ?: return
        val tagIsNewToNote = selectedNote.tags.find { it.id == tag.id } == null

        if (tagIsNewToNote) {
            viewModelScope.launch {
                // todo: _selectedNote.update { updatedNote }
                noteRepository.addTagToNote(selectedNote, tag)
            }
        }
    }

    override fun removeTagFromSelectedNote(tag: Tag) {
        val selectedNote = selectedNote.value ?: return
        val tagExistsInNote = selectedNote.tags.find { it.id == tag.id } != null

        if (tagExistsInNote) {
            viewModelScope.launch {
                // todo: _selectedNote.update { updatedNote }
                noteRepository.removeTagFromNote(selectedNote, tag)
            }
        }
    }

    override fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    override fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.addNote(note)
        }
    }

    override fun filterTags(query: String) {
        _tagSearchQuery.update { query }
    }

    override fun addTag(name: String) {
        val existingTags = _tags.value
        val isNewTagName = existingTags.find { it.name.lowercase() == name.lowercase() } == null

        if(isNewTagName) {
            val newTag = Tag(name = name)

            viewModelScope.launch {
                tagRepository.addTag(newTag)
            }
        }
    }
}
