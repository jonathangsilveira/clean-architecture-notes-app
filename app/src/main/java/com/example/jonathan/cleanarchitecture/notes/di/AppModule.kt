package com.example.jonathan.cleanarchitecture.notes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.jonathan.cleanarchitecture.notes.feature.note.data.datasource.NotesDatabase
import com.example.jonathan.cleanarchitecture.notes.feature.note.data.repository.NoteRepositoryImpl
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.repository.NoteRepository
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            NotesDatabase::class.java,
            NotesDatabase.FILE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NotesDatabase): NoteRepository {
        return NoteRepositoryImpl(dao = database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCases(repo: NoteRepository): NotesUseCases {
        return NotesUseCases(
            getNotes = GetNotesUseCase(repo),
            saveNote = SaveNoteUseCase(repo),
            deleteNote = DeleteNoteUseCase(repo),
            getNote = GetNoteUseCase(repo)
        )
    }

}