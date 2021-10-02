package com.example.jonathan.cleanarchitecture.notes.feature.note.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
    companion object {
        const val FILE_NAME = "notes.db"
    }
}