package ua.nure.liubchenko.lab1.data.asynctasks;

import android.os.AsyncTask;

import ua.nure.liubchenko.lab1.data.models.Note;
import ua.nure.liubchenko.lab1.data.daos.NoteDao;

public class DeleteAsyncTask<Dao extends NoteDao> extends AsyncTask<Note, Void, Void> {

    private Dao noteDao;

    public DeleteAsyncTask(Dao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(final Note... notes) {
        noteDao.delete(notes[0].getNoteId());
        return null;
    }
}
