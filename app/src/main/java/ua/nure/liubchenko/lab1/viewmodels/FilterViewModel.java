package ua.nure.liubchenko.lab1.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.Optional;

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

    private MutableLiveData<Long> date =
            new MutableLiveData<>();

    private LiveData<String> importanceText = Transformations.map(importance, Note.Importance::name);

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
        this.importance.setValue(
                Note.Importance.valueOf(importance.toUpperCase()));
    }

    public void setDate(Long date) {
        this.date.setValue(date);
    }

    public Filter getFilter() {
        Filter filter = new Filter(title.getValue(),
                description.getValue(),
                Optional.ofNullable(date.getValue()).orElse(new Date().getTime()),
                importance.getValue());
        Log.d(TAG, String.format("getFilter: %s", filter.toString()));
        return filter;
    }
}
