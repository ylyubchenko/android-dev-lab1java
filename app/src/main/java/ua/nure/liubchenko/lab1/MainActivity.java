package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import ua.nure.liubchenko.lab1.databinding.MainActivityBinding;
import ua.nure.liubchenko.lab1.ui.notelist.NoteListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.main_activity);
    }
}
