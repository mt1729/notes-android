package com.mt1729.notes.model.repository

import com.mt1729.notes.model.database.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
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
