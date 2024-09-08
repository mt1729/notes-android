package com.mt1729.notes.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import com.mt1729.notes.model.database.entity.NoteEntity
import com.mt1729.notes.model.database.entity.NoteTagEntity
import com.mt1729.notes.model.database.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNotes(vararg notes: NoteEntity)

    @Update
    suspend fun updateNotes(vararg notes: NoteEntity)

    @Delete
    suspend fun deleteNotes(vararg notes: NoteEntity)

    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM note")
    fun getNotesWithTags(): Flow<List<NoteTagsRelation>>

    // todo: get single note by id w/ tags
}

data class NoteTagsRelation(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "note_id",
        entity = TagEntity::class,
        entityColumn = "tag_id",
        associateBy = Junction(
            value = NoteTagEntity::class,
            parentColumn = "note_id",
            entityColumn = "tag_id",
        )
    )
    val tags: List<TagEntity>
)
