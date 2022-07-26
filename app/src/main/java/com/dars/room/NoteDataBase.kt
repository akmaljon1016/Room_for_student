package com.dars.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dars.room.model.Note

@Database(entities = [Note::class], version = 2)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    object DatabaseBuilder {
        private var INSTANCE: NoteDataBase? = null
        fun getDatabase(context: Context): NoteDataBase {
            if (INSTANCE == null) {
                synchronized(NoteDataBase::class.java) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDataBase::class.java, "note_baza"
            ).build()
    }
}