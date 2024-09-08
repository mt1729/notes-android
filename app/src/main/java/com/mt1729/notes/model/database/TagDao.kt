package com.mt1729.notes.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
    @Insert
    suspend fun insert(vararg notes: TagEntity)

    @Update
    suspend fun update(vararg notes: TagEntity)

    @Delete
    suspend fun delete(vararg notes: TagEntity)

    @Query("SELECT * from tag")
    fun getAll(): Flow<List<TagEntity>>

    @Transaction
    @Query("SELECT * FROM tag")
    fun getTagsWithNotes(): Flow<List<TagNotesRelation>>
}
