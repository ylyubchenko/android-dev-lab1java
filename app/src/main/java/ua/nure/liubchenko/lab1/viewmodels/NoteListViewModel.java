package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ua.nure.liubchenko.lab1.persistence.Note;
import ua.nure.liubchenko.lab1.persistence.NoteRepository;

public class NoteListViewModel extends ViewModel {

    private NoteRepository noteRepository;

    private LiveData<List<Note>> allNotes;

    public NoteListViewModel(@NonNull NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        allNotes = noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }
}
