package ua.nure.liubchenko.lab1.data.daos;

import androidx.lifecycle.LiveData;

import java.util.List;

import ua.nure.liubchenko.lab1.data.models.Note;

public interface NoteDao {

    LiveData<List<Note>> getAllNotes();

    LiveData<Note> getNote(long noteId);

    void insert(Note note);

    void insertAll(List<Note> notes);

    int update(Note note);

    void delete(long noteId);
}
