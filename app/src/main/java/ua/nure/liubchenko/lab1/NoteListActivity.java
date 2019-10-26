package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import ua.nure.liubchenko.lab1.adapters.NoteAdapter;
import ua.nure.liubchenko.lab1.databinding.ActivityNoteListBinding;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModel;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModelFactory;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityNoteListBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_note_list);

        NoteListViewModelFactory factory =
                InjectorUtils.provideNoteListViewModelFactory(this);

        NoteListViewModel viewModel =
                ViewModelProviders.of(this, factory).get(NoteListViewModel.class);

        NoteAdapter adapter = new NoteAdapter(this);

        viewModel.getAllNotes().observe(this, adapter::submitList);

        binding.noteList.setAdapter(adapter);
    }
}
