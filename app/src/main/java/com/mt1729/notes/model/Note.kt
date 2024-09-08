package com.mt1729.notes.model

import com.mt1729.notes.model.database.NoteEntity

data class Note(
    val id: Long = -1,
    val title: String,
    val content: String,
    val tags: List<Tag>,
) {
    // todo: dedicated mapper? cleaner
    constructor(from: NoteEntity, tags: List<Tag>?) : this(
        id = from.noteId,
        title = from.title ?: "",
        content = from.content ?: "",
        tags = tags ?: emptyList(),
    )

    fun toEntity(): NoteEntity {
        return NoteEntity(
            noteId = id,
            title = title,
            content = content,
        )
    }
}
