package ua.nure.liubchenko.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Toolbar;

import ua.nure.liubchenko.lab1.databinding.MainActivityBinding;
import ua.nure.liubchenko.lab1.ui.notelist.NoteListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.main_activity);

        NavController navController = Navigation.findNavController(
                this, R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();

        //Toolbar toolbar = findViewById(R.id.toolbar);

        //NavigationUI.setupWithNavController(toolbar, navController);

        NavigationUI.setupActionBarWithNavController(
                this, navController, appBarConfiguration);
    }
}
