package ua.nure.liubchenko.lab1.viewmodels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import ua.nure.liubchenko.lab1.R;
import ua.nure.liubchenko.lab1.persistence.Note;
import ua.nure.liubchenko.lab1.persistence.NoteRepository;

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
            new MutableLiveData<>(0L);

    private MutableLiveData<File> image =
            new MutableLiveData<>(null);

    private LiveData<String> importanceText = Transformations.map(importance, Enum::name);

    private LiveData<String> dateText = Transformations.map(date, d ->
            d == 0 ? "" : DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(d)));

//    private LiveData<String> imagePath = Transformations.map(image, File::getAbsolutePath);
//
//    private LiveData<Bitmap> imageBitmap = Transformations.map(image, f -> {
//        if (f == null) return Bitmap.createBitmap(new Picture());
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        return BitmapFactory.decodeFile(f.getAbsolutePath(), options);
//    });

    public CreateNoteViewModel(@NotNull NoteRepository repository) {
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

//    public LiveData<String> getImagePath() {
//        return imagePath;
//    }
//
//    public LiveData<Bitmap> getImageBitmap() {
//        return imageBitmap;
//    }

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
        Note note = new Note(0, title.getValue(), description.getValue(), date.getValue(),
                importance.getValue(), image.getValue().getAbsolutePath());
        Log.d(TAG, String.format("Saving: %s", note.toString()));
        repository.insert(note);
    }
}
