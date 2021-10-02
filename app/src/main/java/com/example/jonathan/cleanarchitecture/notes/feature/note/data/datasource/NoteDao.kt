package com.example.jonathan.cleanarchitecture.notes.feature.note.data.datasource

import androidx.room.*
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>
    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(note: Note)
    @Delete
    suspend fun delete(note: Note)
}