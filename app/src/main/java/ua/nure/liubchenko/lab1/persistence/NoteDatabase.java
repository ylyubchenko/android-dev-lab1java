package ua.nure.liubchenko.lab1.persistence;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static ua.nure.liubchenko.lab1.utils.Constants.DATABASE_NAME;
import ua.nure.liubchenko.lab1.workers.SeedDatabaseWorker;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteDatabase INSTANCE;

    public static NoteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NoteDatabase.class,
                            DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    OneTimeWorkRequest request =
                                            OneTimeWorkRequest.from(SeedDatabaseWorker.class);
                                    WorkManager.getInstance(context).enqueue(request);
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
