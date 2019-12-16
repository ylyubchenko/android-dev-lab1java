package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.nure.liubchenko.lab1.data.NoteRepository;
import ua.nure.liubchenko.lab1.databinding.ActivityShowNoteBinding;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;
import ua.nure.liubchenko.lab1.viewmodels.ShowNoteViewModel;
import ua.nure.liubchenko.lab1.viewmodels.ShowNoteViewModelFactory;

import ua.nure.liubchenko.lab1.data.Note;

public class ShowNoteActivity extends AppCompatActivity {

    private String TAG = ShowNoteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityShowNoteBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_show_note);

        long noteId = getIntent().getLongExtra(Note.class.getName(), -1);

        ShowNoteViewModelFactory factory =
                InjectorUtils.provideNoteDetailsViewModelFactory(this, noteId);

        ShowNoteViewModel viewModel =
                new ViewModelProvider(this, factory).get(ShowNoteViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        NoteRepository noteRepository = InjectorUtils.getNoteRepository(this);

        noteRepository.getNote(noteId).observe(this, note -> {
            viewModel.setTitle(note.getTitle());
            viewModel.setDescription(note.getDescription());
            viewModel.setImportance(note.getImportance().name());
            viewModel.setImagePath(note.getImagePath());

            if (note.getImagePath() != null) {
                Log.d(TAG, String.format("getNote: image path = %s", note.getImagePath()));

                BitmapFactory.Options options = new BitmapFactory.Options() {{
                    inPreferredConfig = Bitmap.Config.ARGB_8888;
                }};

                Bitmap bitmap = BitmapFactory.decodeFile(note.getImagePath(), options);

                Log.d(TAG, String.format("getNote: image dimensions = %d x %d",
                        bitmap.getWidth(), bitmap.getHeight()));

                binding.showImage.setImageBitmap(bitmap);
            }
        });

        List<String> priorities = Stream.of(Note.Importance.values())
                .map(Note.Importance::name)
                .collect(Collectors.toList());

        ArrayAdapter adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        priorities);

        binding.importance.setAdapter(adapter);

        binding.update.setOnClickListener(v -> viewModel.update());
    }
}
