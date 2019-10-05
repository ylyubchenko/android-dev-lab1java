package ua.nure.liubchenko.lab1.persistence;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.*;

public class NoteRepository {

    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;

    public NoteRepository(NoteDao noteDao) {
        this.noteDao = noteDao;
        allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert(Note note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    private static volatile NoteRepository INSTANCE;

    public static NoteRepository getInstance(NoteDao noteDao) {
        if (INSTANCE == null) {
            synchronized (NoteRepository.class) {
                INSTANCE = new NoteRepository(noteDao);
            }
        }
        return INSTANCE;
    }

    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao asyncTaskDao;

        InsertAsyncTask(NoteDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
