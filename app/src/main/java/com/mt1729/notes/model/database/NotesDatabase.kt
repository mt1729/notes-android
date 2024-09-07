package com.mt1729.notes.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NoteEntity::class, TagEntity::class, NoteTagJoin::class],
    version = NotesDatabase.VERSION
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao

    companion object {
        const val NAME = "notes_database"
        const val VERSION = 1

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(ctx: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    ctx.applicationContext,
                    NotesDatabase::class.java,
                    NAME
                ).build()

                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
