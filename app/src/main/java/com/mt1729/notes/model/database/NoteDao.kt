package com.mt1729.notes.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    fun insertNotes(vararg notes: NoteEntity)

    @Update
    fun updateNotes(vararg notes: NoteEntity)

    @Delete
    fun deleteNotes(vararg notes: NoteEntity)

    @Query("SELECT * FROM note")
    fun getAll(): List<NoteEntity>

    @Transaction
    @Query("SELECT * FROM note")
    fun getNoteTags(): List<NoteTagsRelation>
}
