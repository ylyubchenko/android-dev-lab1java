package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ua.nure.liubchenko.lab1.persistence.NoteRepository;

public class ShowNoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private NoteRepository repository;

    private int noteId;

    public ShowNoteViewModelFactory(NoteRepository repository,
                                    int noteId) {
        this.repository = repository;
        this.noteId = noteId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ShowNoteViewModel(repository, noteId);
    }
}
