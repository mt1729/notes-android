package com.mt1729.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mt1729.notes.model.Note
import com.mt1729.notes.model.Tag
import com.mt1729.notes.model.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface TagSelectionViewModelI {
    val tags: StateFlow<List<Tag>>
    val selectedNote: StateFlow<Note?>
    val filteredTags: StateFlow<List<Tag>>
    val tagSearchQuery: StateFlow<String>

    fun createTag(withName: String) {}
    fun createTag(withName: String, forNote: Note?) {}
    fun filterTags(query: String) {}
    fun setSelectedNote(newNote: Note) {}
    fun addTagToSelectedNote(tag: Tag) {}
    fun selectedNoteContainsTag(tagToFind: Tag): Boolean = false
    fun removeTagFromSelectedNote(tag: Tag) {}
}

// todo: populate VM fields from default state
data class TagSelectionState(
    val tags: List<Tag> = emptyList(),
    val selectedNote: Note? = null,
    val searchTagQuery: String = "",
)

@HiltViewModel
class TagSelectionViewModel @Inject constructor(
    private val tagRepository: TagRepository,
) : ViewModel(), TagSelectionViewModelI {
    private val _selectedNote = MutableStateFlow<Note?>(null)
    private val _tagSearchQuery = MutableStateFlow("")

    override val selectedNote get() = _selectedNote.asStateFlow()
    override val tagSearchQuery get() = _tagSearchQuery.asStateFlow()

    // todo: refactor to set (list checking redundant)
    override val tags = tagRepository.tags
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    override val filteredTags = combine(tags, _tagSearchQuery) { tags, tagSearchQuery ->
        tags.filter {
            it.name.lowercase().startsWith(tagSearchQuery.lowercase())
        }.sortedBy {
            it.name
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    override fun filterTags(query: String) {
        _tagSearchQuery.update { query }
    }

    override fun createTag(withName: String) {
        createTag(withName, forNote = null)
    }

    override fun createTag(withName: String, forNote: Note?) {
        val existingTags = tags.value
        val isNewTagName = existingTags.find { it.name.lowercase() == withName.lowercase() } == null
        val newTag = Tag(name = withName)

        if (isNewTagName && forNote != null) {
            viewModelScope.launch { tagRepository.createTagForNote(newTag, forNote) }
        } else if (isNewTagName) {
            viewModelScope.launch { tagRepository.createTag(newTag) }
        }
    }

    override fun setSelectedNote(newNote: Note) {
        _selectedNote.update { newNote }
    }

    override fun selectedNoteContainsTag(tagToFind: Tag): Boolean {
        val selectedNote = selectedNote.value ?: return false
        val tagExistsInNote = selectedNote.tags.find { it.id == tagToFind.id } != null

        return tagExistsInNote
    }

    override fun addTagToSelectedNote(tag: Tag) {
        val selectedNote = selectedNote.value ?: return

        if (!selectedNoteContainsTag(tag)) {
            viewModelScope.launch {
                tagRepository.addTagToNote(tag, selectedNote)
            }
        }
    }

    override fun removeTagFromSelectedNote(tag: Tag) {
        val selectedNote = selectedNote.value ?: return

        if (selectedNoteContainsTag(tag)) {
            viewModelScope.launch {
                tagRepository.removeTagFromNote(tag, selectedNote)
            }
        }
    }
}
