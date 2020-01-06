package ua.nure.liubchenko.lab1.data.daos;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import ua.nure.liubchenko.lab1.data.models.Note;

public class NoteFileDao implements NoteDao {

    private Context context;

    public NoteFileDao(Context context) {
        this.context = context;
    }

    @Override
    public LiveData<List<Note>> getAllNotes() {
        context.getFilesDir();
        return null;
    }

    @Override
    public LiveData<Note> getNote(long noteId) {
        return null;
    }

    @Override
    public void insert(Note note) {

    }

    @Override
    public void insertAll(List<Note> notes) {

    }

    @Override
    public int update(Note note) {
        return 0;
    }

    @Override
    public void delete(long noteId) {

    }
}
