package com.mt1729.notes.model

import android.content.Context
import com.mt1729.notes.model.database.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providesNotesDatabase(
        @ApplicationContext appCtx: Context,
        coroutineScope: CoroutineScope,
    ): NotesDatabase {
        return NotesDatabase.getDatabase(appCtx, coroutineScope)
    }

    @Singleton
    @Provides
    fun providesNoteRepository(db: NotesDatabase): NoteRepository {
        return NoteRepository(db.noteDao())
    }

    @Singleton
    @Provides
    fun providesTagRepository(db: NotesDatabase): TagRepository {
        return TagRepository(db.tagDao())
    }
}
