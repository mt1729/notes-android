package com.mt1729.notes.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mt1729.notes.model.database.dao.NoteDao
import com.mt1729.notes.model.database.dao.NoteTagDao
import com.mt1729.notes.model.database.dao.TagDao
import com.mt1729.notes.model.database.entity.NoteEntity
import com.mt1729.notes.model.database.entity.NoteTagEntity
import com.mt1729.notes.model.database.entity.TagEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [NoteEntity::class, TagEntity::class, NoteTagEntity::class],
    version = NotesDatabase.VERSION,
    exportSchema = false,
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun tagDao(): TagDao
    abstract fun noteDao(): NoteDao
    abstract fun noteTagDao(): NoteTagDao

    private class NotesDatabaseCallback(
        private val scope: CoroutineScope,
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // todo: test data, remove later
            INSTANCE?.let { notesDb ->
                scope.launch {
                    notesDb.tagDao().insert(*tmpTagEntities)
                    notesDb.noteDao().insertNotes(*tmpNoteEntities)
                    notesDb.noteTagDao().insertNoteTagJoins(*tmpNoteTagJoins)
                }
            }
        }
    }

    companion object {
        const val NAME = "notes_database"
        const val VERSION = 1

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        // todo: not needed under DI
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
