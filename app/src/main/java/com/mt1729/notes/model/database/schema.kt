package com.mt1729.notes.model.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey val noteId: Long,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val content: String?,
)

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey val tagId: Long,
    val name: String,
    val colorHex: String?,
)

@Entity(primaryKeys = ["noteId", "tagId"])
data class NoteTagJoin(
    val noteId: Long,
    val tagId: Long,
)

data class NoteTagsRelation(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NoteTagJoin::class)
    )
    val tags: List<TagEntity>
)

data class TagNotesRelation(
    @Embedded val tag: TagEntity,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "noteId",
        associateBy = Junction(NoteTagJoin::class)
    )
    val notes: List<NoteEntity>
)
