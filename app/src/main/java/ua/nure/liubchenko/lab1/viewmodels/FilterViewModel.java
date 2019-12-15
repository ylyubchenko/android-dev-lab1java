package ua.nure.liubchenko.lab1.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.stream.Stream;

import ua.nure.liubchenko.lab1.data.Filter;
import ua.nure.liubchenko.lab1.data.Note;

public class FilterViewModel extends ViewModel {

    private static String TAG = FilterViewModel.class.getSimpleName();

    private MutableLiveData<String> title =
            new MutableLiveData<>();

    private MutableLiveData<String> description=
            new MutableLiveData<>();

    private MutableLiveData<Note.Importance> importance =
            new MutableLiveData<>();

    // TODO - add date to layout
    private MutableLiveData<Long> date =
            new MutableLiveData<>();

    private LiveData<String> importanceText = Transformations.map(importance, importance ->
            importance != null ? importance.name() : "");

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<String> getDescription() {
        return description;
    }

    public LiveData<String> getImportance() {
        return importanceText;
    }

    public LiveData<Long> getDate() {
        return date;
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

        Log.d(TAG, String.format("setImportance: importance = %s, contains = %b", importance, contains));
        if (contains) {
            this.importance.setValue(Note.Importance.valueOf(importance.toUpperCase()));
        } else {
            this.importance.setValue(null);
        }
    }

    public void setDate(Long date) {
        this.date.setValue(date);
    }

    public void clearFilter() {
        Log.d(TAG, "clearFilter");
        title.setValue(null);
        description.setValue(null);
        importance.setValue(null);
        date.setValue(null);
    }

    public Filter getFilter() {
        Filter filter = new Filter(title.getValue(),
                description.getValue(),
                date.getValue(),
                importance.getValue());
        Log.d(TAG, String.format("getFilter: %s", filter.toString()));
        return filter;
    }
}
