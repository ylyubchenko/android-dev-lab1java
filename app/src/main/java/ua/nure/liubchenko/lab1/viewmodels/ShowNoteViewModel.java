package ua.nure.liubchenko.lab1.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import ua.nure.liubchenko.lab1.data.repos.NoteDbRepository;
import ua.nure.liubchenko.lab1.data.models.Note;
import ua.nure.liubchenko.lab1.data.repos.NoteRepository;

public class ShowNoteViewModel extends ViewModel {

    private static String TAG = ShowNoteViewModel.class.getSimpleName();

    private NoteRepository repository;

    private long noteId;

    private MutableLiveData<String> title =
            new MutableLiveData<>("");

    private MutableLiveData<String> description=
            new MutableLiveData<>("");

    private MutableLiveData<Note.Importance> importance =
            new MutableLiveData<>(Note.Importance.NORMAL);

    private MutableLiveData<Long> date =
            new MutableLiveData<>();

    private MutableLiveData<String> imagePath =
            new MutableLiveData<>();

    private LiveData<String> importanceText = Transformations.map(importance, importance ->
            importance != null ? importance.name() : "");

    private LiveData<String> dateText = Transformations.map(date, d ->
            d == null ? "" : Note.DATE_FORMAT.format(new Date(d)));

    ShowNoteViewModel(@NotNull NoteRepository repository, long noteId) {
        this.repository = repository;
        this.noteId = noteId;
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<String> getDescription() {
        return description;
    }

    public LiveData<String> getImportance() {
        return importanceText;
    }

    public LiveData<String> getDate() {
        return dateText;
    }

    public LiveData<String> getImagePath() {
        return imagePath;
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public void setImportance(String importance) {
        boolean contains = Stream.of(Note.Importance.values())
                .map(Note.Importance::name)
                .anyMatch(im -> im.equalsIgnoreCase(importance));

        Log.d(TAG, String.format("setImportance: importance = %s, contains = %b",
                importance, contains));

        if (contains) {
            this.importance.setValue(Note.Importance.valueOf(importance.toUpperCase()));
        } else {
            this.importance.setValue(null);
        }
    }

    public void setDate(Long date) {
        this.date.setValue(date);
    }

    public void setImagePath(String imagePath) {
        this.imagePath.setValue(imagePath);
    }

    public void update() {
        long dateLong = Optional.ofNullable(date.getValue()).orElse(new Date().getTime());
        Note noteToUpdate = new Note(noteId,
                title.getValue(),
                description.getValue(),
                dateLong,
                importance.getValue(),
                imagePath.getValue());
        Log.d(TAG, String.format("update: %s", noteToUpdate.toString()));
        repository.update(noteToUpdate);
    }
}
