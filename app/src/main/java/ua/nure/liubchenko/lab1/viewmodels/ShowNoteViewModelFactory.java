package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ua.nure.liubchenko.lab1.data.repos.NoteDbRepository;
import ua.nure.liubchenko.lab1.data.repos.NoteRepository;

public class ShowNoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private NoteRepository repository;

    private long noteId;

    public ShowNoteViewModelFactory(NoteRepository repository,
                                    long noteId) {
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
