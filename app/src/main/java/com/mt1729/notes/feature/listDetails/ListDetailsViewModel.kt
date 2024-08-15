package com.mt1729.notes.feature.listDetails

import androidx.lifecycle.ViewModel
import com.mt1729.notes.model.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    private val notesRepository: NoteRepository
) : ViewModel() {
    fun addTagToNote() {

    }

    fun removeTagFromNote() {

    }
}
