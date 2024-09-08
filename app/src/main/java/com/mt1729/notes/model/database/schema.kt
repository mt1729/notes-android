package com.mt1729.notes.model.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id") val noteId: Long,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val content: String?,
)

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id") val tagId: Long,
    @ColumnInfo(name = "name") val name: String, // todo: unique
    @ColumnInfo(name = "color_hex") val colorHex: String?,
)

@Entity(
    tableName = "note_tag",
    primaryKeys = ["note_id", "tag_id"]
)
data class NoteTagJoin(
    @ColumnInfo(name = "note_id") val noteId: Long,
    @ColumnInfo(name = "tag_id") val tagId: Long,
)

data class NoteTagsRelation(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "note_id",
        entityColumn = "tag_id",
        associateBy = Junction(NoteTagJoin::class)
    )
    val tags: List<TagEntity>
)

data class TagNotesRelation(
    @Embedded val tag: TagEntity,
    @Relation(
        parentColumn = "tag_id",
        entityColumn = "note_id",
        associateBy = Junction(NoteTagJoin::class)
    )
    val notes: List<NoteEntity>
)
