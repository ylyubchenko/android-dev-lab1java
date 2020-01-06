package ua.nure.liubchenko.lab1.data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.*;

public class NoteDbRepository implements NoteRepository {

    private static String TAG = NoteDbRepository.class.getSimpleName();

    private NoteDbDao noteDao;

    private LiveData<List<Note>> allNotes;

    public NoteDbRepository(NoteDbDao noteDao) {
        this.noteDao = noteDao;
        allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<Note> getNote(long noteId) {
        return noteDao.getNote(noteId);
    }

    public void insert(Note note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteAsyncTask(noteDao).execute(note);
    }

    private static volatile NoteDbRepository INSTANCE;

    public static NoteDbRepository getInstance(NoteDbDao noteDao) {
        if (INSTANCE == null) {
            synchronized (NoteDbRepository.class) {
                INSTANCE = new NoteDbRepository(noteDao);
            }
        }
        return INSTANCE;
    }

    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDbDao noteDao;

        InsertAsyncTask(NoteDbDao dao) {
            noteDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDbDao noteDao;

        UpdateAsyncTask(NoteDbDao dao) {
            noteDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            int rows = noteDao.update(notes[0]);
            Log.d(TAG, String.format("UpdateAsyncTask: rows = %d", rows));
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDbDao noteDao;

        DeleteAsyncTask(NoteDbDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            noteDao.delete(notes[0].getNoteId());
            return null;
        }
    }
}
