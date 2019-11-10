package ua.nure.liubchenko.lab1.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;

import ua.nure.liubchenko.lab1.persistence.Note;
import ua.nure.liubchenko.lab1.persistence.NoteRepository;

public class CreateNoteViewModel extends ViewModel {

    private static String TAG = CreateNoteViewModel.class.getSimpleName();

    private NoteRepository repository;

    private MutableLiveData<String> title =
            new MutableLiveData<>("");

    private MutableLiveData<String> description=
            new MutableLiveData<>("");

    private MutableLiveData<String> priority=
            new MutableLiveData<>("");

    private MutableLiveData<Long> date =
            new MutableLiveData<>(0L);

    private LiveData<String> dateText = Transformations.map(date, (Long d) ->
        d == 0 ? "" : DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(d)));

    public CreateNoteViewModel(@NotNull NoteRepository repository) {
        this.repository = repository;
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<String> getDescription() {
        return description;
    }

    public LiveData<String> getPriority() {
        return priority;
    }

    public LiveData<String> getDate() {
        return dateText;
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public void setDate(Long date) {
        this.date.setValue(date);
    }

    public void save() {
        Note note = new Note(0, title.getValue(), description.getValue(), date.getValue());
        Log.d(TAG, String.format("Saving: %s", note.toString()));
        repository.insert(note);
    }
}
