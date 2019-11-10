package ua.nure.liubchenko.lab1.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ua.nure.liubchenko.lab1.persistence.NoteRepository;

public class CreateNoteViewModelFactory extends ViewModelProvider.NewInstanceFactory  {

    private NoteRepository repository;

    public CreateNoteViewModelFactory(NoteRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CreateNoteViewModel(repository);
    }
}
