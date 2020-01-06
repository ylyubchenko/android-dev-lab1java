package ua.nure.liubchenko.lab1.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface NoteDao {

    LiveData<List<Note>> getAllNotes();

    LiveData<Note> getNote(long noteId);

    void insert(Note note);

    void insertAll(List<Note> notes);

    int update(Note note);

    void delete(long noteId);
}
