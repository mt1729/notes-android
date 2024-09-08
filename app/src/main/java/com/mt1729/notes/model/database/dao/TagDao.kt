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
interface TagDao {
    @Insert
    suspend fun insert(tag: TagEntity): Long

    @Insert
    suspend fun insert(vararg tags: TagEntity): List<Long>

    @Update
    suspend fun update(vararg tags: TagEntity)

    @Delete
    suspend fun delete(vararg tags: TagEntity)

    @Query("SELECT * from tag")
    fun getAll(): Flow<List<TagEntity>>

    @Transaction
    @Query("SELECT * FROM tag")
    fun getTagsWithNotes(): Flow<List<TagNotesRelation>>

    // todo: get single tag by id w/ notes
}

data class TagNotesRelation(
    @Embedded val tag: TagEntity,
    @Relation(
        parentColumn = "tag_id",
        entity = NoteEntity::class,
        entityColumn = "note_id",
        associateBy = Junction(
            value = NoteTagEntity::class,
            parentColumn = "tag_id",
            entityColumn = "note_id",
        )
    )
    val notes: List<NoteEntity>
)
