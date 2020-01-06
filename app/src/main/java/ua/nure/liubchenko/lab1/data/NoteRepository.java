package ua.nure.liubchenko.lab1.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface NoteRepository {

    LiveData<List<Note>> getAllNotes();

    LiveData<Note> getNote(long noteId);

    void insert(Note note);

    void update(Note note);

    void delete(Note note);
}
