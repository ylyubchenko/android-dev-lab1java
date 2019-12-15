package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

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

        viewModel.getNote().observe(this, note -> {
            if (note != null) {
                Log.d(TAG, note.toString());

                if (note.getImagePath().length() > 0) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(note.getImagePath(), options);

                    binding.showImage.setImageBitmap(bitmap);
                }
            }
        });
//
//        List<String> priorities = Stream.of(Importance.values())
//                .map(Importance::name)
//                .collect(Collectors.toList());
//
//        ArrayAdapter adapter =
//                new ArrayAdapter<>(
//                        this,
//                        R.layout.dropdown_menu_popup_item,
//                        priorities);
//
//        binding.importance.setAdapter(adapter);
    }
}
