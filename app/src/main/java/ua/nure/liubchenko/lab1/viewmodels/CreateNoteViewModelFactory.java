package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ua.nure.liubchenko.lab1.data.repos.NoteDbRepository;

public class CreateNoteViewModelFactory extends ViewModelProvider.NewInstanceFactory  {

    private NoteDbRepository repository;

    public CreateNoteViewModelFactory(NoteDbRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CreateNoteViewModel(repository);
    }
}
