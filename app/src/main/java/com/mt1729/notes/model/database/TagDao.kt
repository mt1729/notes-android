package com.mt1729.notes.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface TagDao {
    @Insert
    fun insert(vararg notes: TagEntity)

    @Update
    fun update(vararg notes: TagEntity)

    @Delete
    fun delete(vararg notes: TagEntity)

    @Query("SELECT * from tag")
    fun getAll(): List<TagEntity>

    @Transaction
    @Query("SELECT * FROM tag")
    fun getTagNotes(): List<TagNotesRelation>
}
