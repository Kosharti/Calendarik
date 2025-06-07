package com.example.calendarik

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface NoteDao {
    @Query("SELECT * FROM new_notes WHERE date = :date ORDER BY startTime")
    fun getNotesForDate(date: LocalDate): Flow<List<Note>>

    @Query("SELECT * FROM new_notes WHERE date BETWEEN :startDate AND :endDate ORDER BY startTime")
    fun getNotesForMonth(startDate: LocalDate, endDate: LocalDate): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note): Int

    @Delete
    suspend fun delete(note: Note): Int

    @Query("SELECT * FROM new_notes ORDER BY date ASC")
    fun getAlphabetizedNotes(): Flow<List<Note>>

    @Query("SELECT * FROM new_notes WHERE id = :id")
    fun getNoteById(id: Long): Flow<Note?>
}