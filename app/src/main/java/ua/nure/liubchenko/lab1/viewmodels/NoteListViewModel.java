package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ua.nure.liubchenko.lab1.data.Note;
import ua.nure.liubchenko.lab1.data.NoteRepository;

public class NoteListViewModel extends ViewModel {

    private NoteRepository noteRepository;

    private LiveData<List<Note>> allNotes;

    NoteListViewModel(@NonNull NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        allNotes = noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }
}
