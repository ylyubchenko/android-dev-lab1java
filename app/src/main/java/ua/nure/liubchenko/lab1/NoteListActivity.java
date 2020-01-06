package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ua.nure.liubchenko.lab1.adapters.NoteAdapter;
import ua.nure.liubchenko.lab1.data.models.Filter;
import ua.nure.liubchenko.lab1.data.models.Note;
import ua.nure.liubchenko.lab1.databinding.ActivityNoteListBinding;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModel;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModelFactory;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;

public class NoteListActivity extends AppCompatActivity {

    private static String TAG = NoteListActivity.class.getSimpleName();

    private NoteAdapter noteAdapter;

    private NoteListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityNoteListBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_note_list);

        binding.toolbar.inflateMenu(R.menu.menu_main);

        binding.toolbar.setOnMenuItemClickListener(item -> {
            Log.d(TAG, item.toString());
            FilterDialog
                    .withApplicationHandler(this::filterHandler)
                    .show(getSupportFragmentManager());
            return true;
        });

        NoteListViewModelFactory factory =
                InjectorUtils.provideFileNoteListViewModelFactory(this);

        viewModel = new ViewModelProvider(this, factory).get(NoteListViewModel.class);

        noteAdapter = new NoteAdapter(this);

        binding.notes.setAdapter(noteAdapter);

        viewModel.getAllNotes().observe(this, notes -> {
            noteAdapter.setNotes(notes);
            noteAdapter.notifyDataSetChanged();
        });

        binding.createNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateNoteActivity.class);
            this.startActivity(intent);
        });
    }

    private void filterHandler(Filter filter) {
        Log.d(TAG, "filterHandler");
        List<Note> notes = viewModel.getAllNotes().getValue();

        if (notes != null) {
            List<Note> filteredNotes = filter.apply(notes);
            noteAdapter.setNotes(filteredNotes);
            noteAdapter.notifyDataSetChanged();
        }
    }
}
