package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ua.nure.liubchenko.lab1.data.repos.NoteDbRepository;
import ua.nure.liubchenko.lab1.data.repos.NoteRepository;

public class FilterViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FilterViewModel();
    }

}
