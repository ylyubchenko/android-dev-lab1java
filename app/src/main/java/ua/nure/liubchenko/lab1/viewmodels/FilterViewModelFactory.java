package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ua.nure.liubchenko.lab1.data.repos.NoteDbRepository;

public class FilterViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private NoteDbRepository noteRepository;

    public FilterViewModelFactory(NoteDbRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FilterViewModel();
    }

}
