package ua.nure.liubchenko.lab1.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * from notes ORDER BY id ASC")
    LiveData<List<Note>> getAllNotes();

    @Insert
    void insert(Note note);

    @Insert
    void insertAll(List<Note> notes);

    @Query("DELETE FROM notes")
    void deleteAll();
}
