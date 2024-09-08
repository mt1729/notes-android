package com.mt1729.notes.model

import com.mt1729.notes.model.database.TagEntity

data class Tag(
    val id: Long? = null,
    val name: String,
    val colorHex: String? = null,
) {
    // todo: dedicated mapper? cleaner
    constructor(from: TagEntity) : this(
        id = from.tagId,
        name = from.name,
        colorHex = from.colorHex,
    )

    fun toEntity(): TagEntity {
        return TagEntity(
            tagId = id ?: 0,
            name = name,
            colorHex = colorHex,
        )
    }
}
