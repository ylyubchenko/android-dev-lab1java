package ua.nure.liubchenko.lab1.utils;

import android.content.Context;

import ua.nure.liubchenko.lab1.persistence.NoteDatabase;
import ua.nure.liubchenko.lab1.persistence.NoteRepository;
import ua.nure.liubchenko.lab1.viewmodels.CreateNoteViewModelFactory;
import ua.nure.liubchenko.lab1.viewmodels.ShowNoteViewModelFactory;
import ua.nure.liubchenko.lab1.viewmodels.NoteListViewModelFactory;

public class InjectorUtils {

    private static NoteRepository getNoteRepository(Context context) {
        return NoteRepository.getInstance(
                NoteDatabase.getDatabase(context.getApplicationContext()).noteDao());
    }

    public static NoteListViewModelFactory provideNoteListViewModelFactory(Context context) {
        return new NoteListViewModelFactory(getNoteRepository(context));
    }

    public static ShowNoteViewModelFactory provideNoteDetailsViewModelFactory(Context context,
                                                                              int noteId) {
        return new ShowNoteViewModelFactory(getNoteRepository(context), noteId);
    }

    public static CreateNoteViewModelFactory provideCreateNoteViewModelFactory(Context context) {
        return new CreateNoteViewModelFactory(getNoteRepository(context));
    }
}
