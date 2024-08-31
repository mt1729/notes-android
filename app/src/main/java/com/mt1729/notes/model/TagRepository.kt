package com.mt1729.notes.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TagRepository {
    fun getTags(): Flow<List<Tag>> = flow {
        // todo: move test data
        val tags = listOf(
            Tag("Tag 1"),
            Tag("Tag 2"),
            Tag("Tag 3"),
            Tag("Tag 4"),
            Tag("Tag 5"),
            Tag("Tag 6"),
            Tag("Tag with a medium length name 1"),
            Tag("Tag with a medium length name 2"),
            Tag("Tag with a medium length name 3"),
            Tag("Tag with a medium length name 4"),
            Tag("Looooooooooooooooooooooooooooooooooooooooooooooooooong tag name 1"),
            Tag("Loooooooooooooooooooooooooooooooooooooooooooooooooooooonger tag name "),
        )

        emit(tags.shuffled())
    }

    fun updateTag(tag: Tag) {}
    fun deleteTag(tag: Tag) {}
    fun addTag(tag: Tag) {}
}
