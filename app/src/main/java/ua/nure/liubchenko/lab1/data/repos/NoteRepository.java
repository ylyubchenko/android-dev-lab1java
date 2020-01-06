package ua.nure.liubchenko.lab1.data.repos;

import androidx.lifecycle.LiveData;

import java.util.List;

import ua.nure.liubchenko.lab1.data.models.Note;

public interface NoteRepository {

    LiveData<List<Note>> getAllNotes();

    LiveData<Note> getNote(long noteId);

    void insert(Note note);

    void update(Note note);

    void delete(Note note);
}
