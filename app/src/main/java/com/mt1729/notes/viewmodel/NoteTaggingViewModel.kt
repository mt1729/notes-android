package com.mt1729.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mt1729.notes.model.Note
import com.mt1729.notes.model.Tag
import com.mt1729.notes.model.repository.NoteRepository
import com.mt1729.notes.model.repository.TagRepository
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
    fun selectNoteIndex(index: Int) {}
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
    override val notes = noteRepository.getNotes()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // todo: refactor to set (list checking redundant)
    override val tags = tagRepository.getTags()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _tagSearchQuery = MutableStateFlow("")
    override val tagSearchQuery get() = _tagSearchQuery.asStateFlow()


    private val _selectedNoteIndex = MutableStateFlow(0)
    override val selectedNote get() = combine(notes, _selectedNoteIndex) { notes, selectedNoteIndex ->
        notes.getOrNull(selectedNoteIndex)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    override val filteredTags get() = combine(tags, _tagSearchQuery) { tags, tagSearchQuery ->
        tags.filter {
            it.name.lowercase().startsWith(tagSearchQuery.lowercase())
        }.sortedBy {
            it.name
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    override val selectedNoteHeader = selectedNote.map { selectedNote ->
        val notes = notes.value
        val currNoteIndex = notes.indexOfFirst { it.id == selectedNote?.id }

        return@map if (selectedNote == null || currNoteIndex < 0) {
            ""
        } else {
            "${currNoteIndex + 1} / ${notes.size}"
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    override fun selectNoteIndex(index: Int) {
        _selectedNoteIndex.update { index }
    }

    override fun addTagToSelectedNote(tag: Tag) {
        val selectedNote = selectedNote.value ?: return
        val tagIsNewToNote = selectedNote.tags.find { it.id == tag.id } == null

        if (tagIsNewToNote) {
            viewModelScope.launch {
                noteRepository.addTagToNote(selectedNote, tag)
            }
        }
    }

    override fun removeTagFromSelectedNote(tag: Tag) {
        val selectedNote = selectedNote.value ?: return
        val tagExistsInNote = selectedNote.tags.find { it.id == tag.id } != null

        if (tagExistsInNote) {
            viewModelScope.launch {
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
        val existingTags = tags.value
        val isNewTagName = existingTags.find { it.name.lowercase() == name.lowercase() } == null

        if(isNewTagName) {

            viewModelScope.launch {
                val tagToCreate = Tag(name = name)
                val createdTag = tagRepository.addTag(tagToCreate)


                // Default behavior when creating a tag (assume it's for the note)
                val selectedNote = selectedNote.value ?: return@launch
                noteRepository.addTagToNote(selectedNote, createdTag)
            }
        }
    }
}
