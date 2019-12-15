package ua.nure.liubchenko.lab1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import ua.nure.liubchenko.lab1.data.NoteRepository;
import ua.nure.liubchenko.lab1.data.Note;

public class ShowNoteViewModel extends ViewModel {

    private LiveData<Note> note;

    ShowNoteViewModel(@NotNull NoteRepository repository, long noteId) {
        this.note = repository.getNote(noteId);
    }

    public LiveData<Note> getNote() {
        return note;
    }
}
