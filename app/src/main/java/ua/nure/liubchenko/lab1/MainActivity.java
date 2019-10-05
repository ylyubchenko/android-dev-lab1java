package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ua.nure.liubchenko.lab1.ui.notelist.NoteListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NoteListFragment.newInstance())
                    .commitNow();
        }
    }
}
