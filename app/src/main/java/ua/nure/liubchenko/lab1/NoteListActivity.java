package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import ua.nure.liubchenko.lab1.adapters.NoteAdapter;
import ua.nure.liubchenko.lab1.databinding.ActivityNoteListBinding;
import ua.nure.liubchenko.lab1.persistence.Note;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModel;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModelFactory;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;

public class NoteListActivity extends AppCompatActivity {

    private static String TAG = NoteListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityNoteListBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_note_list);

        binding.toolbar.inflateMenu(R.menu.menu_main);

        NoteListViewModelFactory factory =
                InjectorUtils.provideNoteListViewModelFactory(this);

        NoteListViewModel viewModel =
                new ViewModelProvider(this, factory).get(NoteListViewModel.class);

        NoteAdapter adapter = new NoteAdapter(this);

        binding.notes.setAdapter(adapter);

        viewModel.getAllNotes().observe(this, adapter::setNotes);

        binding.createNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateNoteActivity.class);
            this.startActivity(intent);
        });
    }

    public void removeNote(View view, Note note) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("Ok", null)
                .show();
    }
}
