package com.mt1729.notes.model

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val tags: List<Tag>,
    val content: String,
)
