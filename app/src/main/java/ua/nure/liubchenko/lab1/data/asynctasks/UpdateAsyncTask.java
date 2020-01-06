package ua.nure.liubchenko.lab1.data.asynctasks;

import android.os.AsyncTask;

import ua.nure.liubchenko.lab1.data.models.Note;
import ua.nure.liubchenko.lab1.data.daos.NoteDao;

public class UpdateAsyncTask<Dao extends NoteDao> extends AsyncTask<Note, Void, Void> {

    private Dao noteDao;

    public UpdateAsyncTask(Dao dao) {
        noteDao = dao;
    }

    @Override
    protected Void doInBackground(final Note... notes) {
        int rows = noteDao.update(notes[0]);
        return null;
    }
}
