package ua.nure.liubchenko.lab1.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import ua.nure.liubchenko.lab1.data.models.Note;
import ua.nure.liubchenko.lab1.data.NoteDatabase;

import static ua.nure.liubchenko.lab1.utils.Constants.NOTE_DATA_FILENAME;

public class SeedDatabaseWorker extends Worker {

    private static String TAG = SeedDatabaseWorker.class.getSimpleName();

    public SeedDatabaseWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try (
                InputStream stream = getApplicationContext()
                        .getAssets()
                        .open(NOTE_DATA_FILENAME);
                Reader reader = new InputStreamReader(stream);
                JsonReader jsonReader = new JsonReader(reader);
        ) {
            Type noteType = new TypeToken<List<Note>>(){}.getType();
            List<Note> notes = new Gson().fromJson(jsonReader, noteType);

            notes.forEach(note -> Log.d(TAG, "Parsed " + note.toString()));

            NoteDatabase db = NoteDatabase.getDatabase(getApplicationContext());
            db.noteDao().insertAll(notes);

            return Result.success();
        } catch (Exception ex) {
            Log.e(TAG, "Error seeding database", ex);
            return Result.failure();
        }
    }
}
