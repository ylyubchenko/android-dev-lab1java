package ua.nure.liubchenko.lab1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.nure.liubchenko.lab1.data.repos.NoteDbRepository;
import ua.nure.liubchenko.lab1.databinding.ActivityShowNoteBinding;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;
import ua.nure.liubchenko.lab1.viewmodels.ShowNoteViewModel;
import ua.nure.liubchenko.lab1.viewmodels.ShowNoteViewModelFactory;

import ua.nure.liubchenko.lab1.data.models.Note;

public class ShowNoteActivity extends AppCompatActivity {

    private String TAG = ShowNoteActivity.class.getSimpleName();

    private ShowNoteViewModel viewModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityShowNoteBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_show_note);

        long noteId = getIntent().getLongExtra(Note.class.getName(), -1);

        ShowNoteViewModelFactory factory =
                InjectorUtils.provideNoteDetailsViewModelFactory(this, noteId);

        viewModel = new ViewModelProvider(this, factory).get(ShowNoteViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        NoteDbRepository noteRepository = InjectorUtils.getNoteDbRepository(this);

        noteRepository.getNote(noteId).observe(this, note -> {
            viewModel.setTitle(note.getTitle());
            viewModel.setDescription(note.getDescription());
            viewModel.setImportance(note.getImportance().name());
            viewModel.setDate(note.getDate());
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

        binding.date.setOnTouchListener((View v, MotionEvent e) -> {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().build();
                picker.show(getSupportFragmentManager(), picker.toString());
                picker.addOnPositiveButtonClickListener(viewModel::setDate);
            }

            return true;
        });

        binding.image.setOnTouchListener((View v, MotionEvent e) -> {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                ImagePicker.Companion.with(this)
                        .start();
            }

            return true;
        });

        binding.title.addTextChangedListener(provideTextWatcher(viewModel::setTitle));

        binding.desc.addTextChangedListener(provideTextWatcher(viewModel::setDescription));

        binding.importance.addTextChangedListener(provideTextWatcher(viewModel::setImportance));

        binding.update.setOnClickListener(v -> {
            viewModel.update();
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            File file = ImagePicker.Companion.getFile(data);
            Log.d(TAG, file.getAbsolutePath());
            viewModel.setImagePath(file.getAbsolutePath());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private TextWatcher provideTextWatcher(Consumer<String> setter) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setter.accept(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }
}
