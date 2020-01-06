package ua.nure.liubchenko.lab1.data.repos;

import androidx.lifecycle.LiveData;

import java.util.List;

import ua.nure.liubchenko.lab1.data.models.Note;
import ua.nure.liubchenko.lab1.data.daos.NoteFileDao;
import ua.nure.liubchenko.lab1.data.asynctasks.DeleteAsyncTask;
import ua.nure.liubchenko.lab1.data.asynctasks.InsertAsyncTask;
import ua.nure.liubchenko.lab1.data.asynctasks.UpdateAsyncTask;

public class NoteFileRepository implements NoteRepository {

    private NoteFileDao noteDao;

    private LiveData<List<Note>> allNotes;

    public NoteFileRepository(NoteFileDao noteDao) {
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

    private static volatile NoteFileRepository INSTANCE;

    public static NoteFileRepository getInstance(NoteFileDao noteDao) {
        if (INSTANCE == null) {
            synchronized (NoteFileRepository.class) {
                INSTANCE = new NoteFileRepository(noteDao);
            }
        }
        return INSTANCE;
    }
}
