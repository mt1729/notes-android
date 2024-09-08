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
