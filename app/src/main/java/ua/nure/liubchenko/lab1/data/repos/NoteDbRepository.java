package ua.nure.liubchenko.lab1.data.repos;

import androidx.lifecycle.LiveData;

import java.util.*;

import ua.nure.liubchenko.lab1.data.models.Note;
import ua.nure.liubchenko.lab1.data.daos.NoteDbDao;
import ua.nure.liubchenko.lab1.data.asynctasks.DeleteAsyncTask;
import ua.nure.liubchenko.lab1.data.asynctasks.InsertAsyncTask;
import ua.nure.liubchenko.lab1.data.asynctasks.UpdateAsyncTask;

public class NoteDbRepository implements NoteRepository {

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
        new InsertAsyncTask<>(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateAsyncTask<>(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteAsyncTask<>(noteDao).execute(note);
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
}
