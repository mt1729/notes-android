package com.mt1729.notes.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNotes(vararg notes: NoteEntity)

    @Insert
    suspend fun insertNoteTagJoins(vararg joins: NoteTagJoin)

    @Update
    suspend fun updateNotes(vararg notes: NoteEntity)

    @Delete
    suspend fun deleteNotes(vararg notes: NoteEntity)

    @Delete
    suspend fun deleteNoteTagJoins(vararg joins: NoteTagJoin)

    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM note")
    fun getNotesWithTags(): Flow<List<NoteTagsRelation>>
}
