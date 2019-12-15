package ua.nure.liubchenko.lab1.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ua.nure.liubchenko.lab1.persistence.Note;

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

}
