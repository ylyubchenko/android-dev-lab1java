package ua.nure.liubchenko.lab1.utils;

import android.content.Context;

import ua.nure.liubchenko.lab1.data.NoteDatabase;
import ua.nure.liubchenko.lab1.data.daos.NoteFileDao;
import ua.nure.liubchenko.lab1.data.repos.NoteDbRepository;
import ua.nure.liubchenko.lab1.data.repos.NoteFileRepository;
import ua.nure.liubchenko.lab1.viewmodels.CreateNoteViewModelFactory;
import ua.nure.liubchenko.lab1.viewmodels.FilterViewModelFactory;
import ua.nure.liubchenko.lab1.viewmodels.ShowNoteViewModelFactory;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModelFactory;

public class InjectorUtils {

    public static NoteDbRepository getNoteDbRepository(Context context) {
        return NoteDbRepository.getInstance(
                NoteDatabase.getDatabase(context.getApplicationContext()).noteDao());
    }

    public static NoteFileRepository getNoteFileRepository(Context context) {
        return NoteFileRepository.getInstance(new NoteFileDao(context.getApplicationContext()));
    }

    public static NoteListViewModelFactory provideNoteListViewModelFactory(Context context) {
        return new NoteListViewModelFactory(getNoteDbRepository(context));
    }

    public static ShowNoteViewModelFactory provideNoteDetailsViewModelFactory(Context context,
                                                                              long noteId) {
        return new ShowNoteViewModelFactory(getNoteDbRepository(context), noteId);
    }

    public static CreateNoteViewModelFactory provideCreateNoteViewModelFactory(Context context) {
        return new CreateNoteViewModelFactory(getNoteDbRepository(context));
    }

    public static FilterViewModelFactory provideFilterViewModelFactory(Context context) {
        return new FilterViewModelFactory(getNoteDbRepository(context));
    }
}
