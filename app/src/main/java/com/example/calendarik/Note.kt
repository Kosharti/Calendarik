package com.example.calendarik

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "new_notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val eventName: String,
    val noteText: String,
    val date: LocalDate,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val category: String,
    val reminderEnabled: Boolean,
    @ColumnInfo(defaultValue = "#735BF2")
    val color: String = "#735BF2"
)