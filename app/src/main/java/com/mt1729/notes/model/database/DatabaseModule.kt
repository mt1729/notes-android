package com.mt1729.notes.model.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesNotesDatabase(
        @ApplicationContext appCtx: Context,
        coroutineScope: CoroutineScope,
    ): NotesDatabase {
        return NotesDatabase.getDatabase(appCtx, coroutineScope)
    }
}
