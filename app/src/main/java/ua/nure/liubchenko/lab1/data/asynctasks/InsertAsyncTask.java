package ua.nure.liubchenko.lab1.data.asynctasks;

import android.os.AsyncTask;

import ua.nure.liubchenko.lab1.data.models.Note;
import ua.nure.liubchenko.lab1.data.daos.NoteDao;

public class InsertAsyncTask<Dao extends NoteDao> extends AsyncTask<Note, Void, Void> {

    private Dao noteDao;

    public InsertAsyncTask(Dao dao) {
        noteDao = dao;
    }

    @Override
    protected Void doInBackground(final Note... notes) {
        noteDao.insert(notes[0]);
        return null;
    }
}