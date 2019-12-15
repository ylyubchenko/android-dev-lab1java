package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import ua.nure.liubchenko.lab1.adapters.NoteAdapter;
import ua.nure.liubchenko.lab1.databinding.ActivityNoteListBinding;
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

        binding.toolbar.setOnMenuItemClickListener(item -> {
            Log.d(TAG, item.toString());
            FilterDialog.show(getSupportFragmentManager());
            return true;
        });

        NoteListViewModelFactory factory =
                InjectorUtils.provideNoteListViewModelFactory(this);

        NoteListViewModel viewModel =
                new ViewModelProvider(this, factory).get(NoteListViewModel.class);

        NoteAdapter adapter = new NoteAdapter(this);

        binding.notes.setAdapter(adapter);

        viewModel.getAllNotes().observe(this, notes -> {
            adapter.setNotes(notes);
            adapter.notifyDataSetChanged();
        });

        binding.createNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateNoteActivity.class);
            this.startActivity(intent);
        });
    }
}
