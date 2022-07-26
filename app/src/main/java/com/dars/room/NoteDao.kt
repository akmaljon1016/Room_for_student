package com.dars.room

import androidx.room.*
import com.dars.room.model.Note

@Dao
interface NoteDao {
    @Insert
   suspend fun insertNote(note: Note)

    @Delete
     fun deleteNote(note: Note)

    @Update
     fun update(note: Note)

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): List<Note>

}