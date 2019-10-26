package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import ua.nure.liubchenko.lab1.databinding.NoteDetailsActivityBinding;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;
import ua.nure.liubchenko.lab1.viewmodels.NoteDetailsViewModel;
import ua.nure.liubchenko.lab1.viewmodels.NoteDetailsViewModelFactory;

public class NoteDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NoteDetailsActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.note_details_activity);

        Intent intent = getIntent();

        int noteId = intent.getIntExtra("ua.nure.liubchenko.lab1.NoteId", -1);

        if (noteId != -1) {

        }

        NoteDetailsViewModelFactory factory =
                InjectorUtils.provideNoteDetailsViewModelFactory(this, noteId);

        NoteDetailsViewModel viewModel =
                new ViewModelProvider(this, factory).get(NoteDetailsViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);
    }
}
