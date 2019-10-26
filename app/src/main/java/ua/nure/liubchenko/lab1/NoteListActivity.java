package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import ua.nure.liubchenko.lab1.adapters.NoteAdapter;
import ua.nure.liubchenko.lab1.databinding.NoteListActivityBinding;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModel;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModelFactory;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NoteListActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.note_list_activity);

        NoteListViewModelFactory factory =
                InjectorUtils.provideNoteListViewModelFactory(this);

        NoteListViewModel viewModel =
                new ViewModelProvider(this, factory).get(NoteListViewModel.class);

        NoteAdapter adapter = new NoteAdapter(this);

        viewModel.getAllNotes().observe(this, adapter::submitList);

        binding.noteList.setAdapter(adapter);

        binding.createNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteDetailsActivity.class);
            this.startActivity(intent);
        });
    }
}
