package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.nure.liubchenko.lab1.databinding.ActivityCreateNoteBinding;
import ua.nure.liubchenko.lab1.persistence.Note;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;
import ua.nure.liubchenko.lab1.viewmodels.CreateNoteViewModel;
import ua.nure.liubchenko.lab1.viewmodels.CreateNoteViewModelFactory;

public class CreateNoteActivity extends AppCompatActivity {

    private String TAG = CreateNoteActivity.class.getSimpleName();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCreateNoteBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_create_note);

        CreateNoteViewModelFactory factory =
                InjectorUtils.provideCreateNoteViewModelFactory(this);

        CreateNoteViewModel viewModel =
                new ViewModelProvider(this, factory).get(CreateNoteViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        List<String> priorities = Stream.of(Note.Importance.values())
                .map(Note.Importance::name)
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase())
                .collect(Collectors.toList());

        ArrayAdapter adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        priorities);

        binding.importance.setAdapter(adapter);

        binding.date.setOnTouchListener((View v, MotionEvent e) -> {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();

                MaterialDatePicker<Long> picker = builder.build();

                picker.show(getSupportFragmentManager(), picker.toString());

                picker.addOnPositiveButtonClickListener(viewModel::setDate);
            }

            return true;
        });

        binding.title.addTextChangedListener(provideTextWatcher(viewModel::setTitle));

        binding.desc.addTextChangedListener(provideTextWatcher(viewModel::setDescription));

        binding.save.setOnClickListener(v -> {
            viewModel.save();
            finish();
        });
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
