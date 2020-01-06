package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ua.nure.liubchenko.lab1.data.Note;
import ua.nure.liubchenko.lab1.data.NoteDbRepository;

public class NoteListViewModel extends ViewModel {

    private LiveData<List<Note>> allNotes;

    NoteListViewModel(@NonNull NoteDbRepository noteRepository) {
        allNotes = noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
