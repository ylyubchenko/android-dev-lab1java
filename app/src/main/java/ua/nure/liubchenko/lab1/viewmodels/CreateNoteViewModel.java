package ua.nure.liubchenko.lab1.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Date;
import java.util.Optional;

import ua.nure.liubchenko.lab1.data.Note;
import ua.nure.liubchenko.lab1.data.NoteRepository;

public class CreateNoteViewModel extends ViewModel {

    private static String TAG = CreateNoteViewModel.class.getSimpleName();

    private NoteRepository repository;

    private MutableLiveData<String> title =
            new MutableLiveData<>("");

    private MutableLiveData<String> description=
            new MutableLiveData<>("");

    private MutableLiveData<Note.Importance> importance =
            new MutableLiveData<>(Note.Importance.NORMAL);

    private MutableLiveData<Long> date =
            new MutableLiveData<>();

    private MutableLiveData<File> image =
            new MutableLiveData<>();

    // TODO - handle importance typed by hand
    private LiveData<String> importanceText = Transformations.map(importance, Note.Importance::name);

    private LiveData<String> dateText = Transformations.map(date, d ->
            d == null ? "" : Note.DATE_FORMAT.format(new Date(d)));

    CreateNoteViewModel(@NotNull NoteRepository repository) {
        this.repository = repository;
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

    public LiveData<File> getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public void setImportance(String importance) {
        this.importance.setValue(
                Note.Importance.valueOf(importance.toUpperCase()));
    }

    public void setDate(Long date) {
        this.date.setValue(date);
    }

    public void setImage(File image) {
        this.image.setValue(image);
    }

    public void save() {
        long dateLong = Optional.ofNullable(date.getValue())
                .orElse(new Date().getTime());
        String imagePath = Optional.ofNullable(image.getValue()).map(File::getAbsolutePath)
                .orElse("");
        Note note = new Note(0, title.getValue(), description.getValue(), dateLong,
                importance.getValue(), imagePath);
        Log.d(TAG, String.format("Saving: %s", note.toString()));
        repository.insert(note);
    }
}
