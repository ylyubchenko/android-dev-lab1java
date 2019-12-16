package ua.nure.liubchenko.lab1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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

import ua.nure.liubchenko.lab1.databinding.ActivityCreateNoteBinding;
import ua.nure.liubchenko.lab1.data.Note;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;
import ua.nure.liubchenko.lab1.viewmodels.CreateNoteViewModel;
import ua.nure.liubchenko.lab1.viewmodels.CreateNoteViewModelFactory;

public class CreateNoteActivity extends AppCompatActivity {

    private String TAG = CreateNoteActivity.class.getSimpleName();

    ActivityCreateNoteBinding binding;
    CreateNoteViewModel viewModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_note);

        CreateNoteViewModelFactory factory =
                InjectorUtils.provideCreateNoteViewModelFactory(this);

        viewModel = new ViewModelProvider(this, factory).get(CreateNoteViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

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

        binding.save.setOnClickListener(v -> {
            viewModel.save();
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
