package com.mt1729.notes.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [NoteEntity::class, TagEntity::class, NoteTagJoin::class],
    version = NotesDatabase.VERSION,
    exportSchema = false,
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao

    private class NotesDatabaseCallback(
        private val scope: CoroutineScope,
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // todo: test data, remove later
            INSTANCE?.let { notesDb ->
                scope.launch {
                    val tagDao = notesDb.tagDao()
                    val noteDao = notesDb.noteDao()

                    tagDao.insert(*tmpTagEntities)
                    noteDao.insertNotes(*tmpNoteEntities)
                    noteDao.insertNoteTagJoins(*tmpNoteTagJoins)
                }
            }
        }
    }


    companion object {
        const val NAME = "notes_database"
        const val VERSION = 1

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(ctx: Context, scope: CoroutineScope): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    ctx.applicationContext,
                    NotesDatabase::class.java,
                    NAME
                ).addCallback(NotesDatabaseCallback(scope)).build()

                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
