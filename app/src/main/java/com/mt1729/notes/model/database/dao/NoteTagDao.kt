package com.mt1729.notes.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.mt1729.notes.model.database.entity.NoteTagEntity

@Dao
interface NoteTagDao {
    @Insert
    suspend fun insertNoteTagJoins(vararg joins: NoteTagEntity)

    @Delete
    suspend fun deleteNoteTagJoins(vararg joins: NoteTagEntity)
}
