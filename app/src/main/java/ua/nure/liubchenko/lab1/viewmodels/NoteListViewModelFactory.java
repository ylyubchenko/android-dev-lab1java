package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ua.nure.liubchenko.lab1.data.NoteRepository;

public class NoteListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private NoteRepository noteRepository;

    public NoteListViewModelFactory(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteListViewModel(noteRepository);
    }
}
