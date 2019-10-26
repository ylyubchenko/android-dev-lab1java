package ua.nure.liubchenko.lab1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import ua.nure.liubchenko.lab1.persistence.NoteRepository;
import ua.nure.liubchenko.lab1.persistence.Note;

public class NoteDetailsViewModel extends ViewModel {

    private NoteRepository repository;

    private LiveData<Note> note;

    public NoteDetailsViewModel(@NotNull NoteRepository repository,
                                int noteId) {
        this.repository = repository;
        this.note = repository.getNote(noteId);
    }

    public LiveData<Note> getNote() {
        return note;
    }
}
