package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.nure.liubchenko.lab1.databinding.ActivityShowNoteBinding;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;
import ua.nure.liubchenko.lab1.viewmodels.ShowNoteViewModel;
import ua.nure.liubchenko.lab1.viewmodels.ShowNoteViewModelFactory;

import ua.nure.liubchenko.lab1.persistence.Note;
import ua.nure.liubchenko.lab1.persistence.Note.Importance;

public class ShowNoteActivity extends AppCompatActivity {

    private String TAG = ShowNoteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityShowNoteBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_show_note);

        int noteId = getIntent().getIntExtra(Note.class.getName(), -1);

        ShowNoteViewModelFactory factory =
                InjectorUtils.provideNoteDetailsViewModelFactory(this, noteId);

        ShowNoteViewModel viewModel =
                new ViewModelProvider(this, factory).get(ShowNoteViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        viewModel.getNote().observe(this, note -> Log.d(TAG, note.toString()));

        List<String> priorities = Stream.of(Importance.values())
                .map(Importance::name)
                .collect(Collectors.toList());

        ArrayAdapter adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        priorities);

        binding.importance.setAdapter(adapter);
    }
}
