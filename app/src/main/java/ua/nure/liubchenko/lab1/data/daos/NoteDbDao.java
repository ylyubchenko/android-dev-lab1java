package ua.nure.liubchenko.lab1.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ua.nure.liubchenko.lab1.data.models.Note;

@Dao
public interface NoteDbDao extends NoteDao {

    @Query("SELECT * from notes ORDER BY id ASC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM notes WHERE id = :noteId LIMIT 1")
    LiveData<Note> getNote(long noteId);

    @Insert
    void insert(Note note);

    @Insert
    void insertAll(List<Note> notes);

    @Update
    int update(Note note);

    @Query("DELETE FROM notes WHERE id = :noteId")
    void delete(long noteId);
}
