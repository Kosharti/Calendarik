package com.example.calendarik

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 3, exportSchema = true, autoMigrations = [ AutoMigration(from = 1, to = 2)])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )   .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE new_notes (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        eventName TEXT NOT NULL,
                        noteText TEXT NOT NULL,
                        date TEXT NOT NULL,
                        startTime TEXT,
                        endTime TEXT,
                        category TEXT NOT NULL,
                        reminderEnabled INTEGER NOT NULL,
                        color TEXT NOT NULL DEFAULT '#735BF2'
                    )
                """)

                database.execSQL("""
                    INSERT INTO new_notes 
                    (id, eventName, noteText, date, startTime, endTime, category, reminderEnabled, color)
                    SELECT 
                    id, 
                    COALESCE(eventName, '') as eventName,
                    COALESCE(noteText, '') as noteText,
                    COALESCE(date, '') as date,
                    startTime,
                    endTime,
                    COALESCE(category, '') as category,
                    COALESCE(reminderEnabled, 0) as reminderEnabled,
                    COALESCE(color, '#735BF2') as color
                    FROM notes
                """)

                database.execSQL("DROP TABLE notes")
            }
        }
    }
}
