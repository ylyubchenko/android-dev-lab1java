package ua.nure.liubchenko.lab1.data.daos;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ua.nure.liubchenko.lab1.data.models.Note;

import static ua.nure.liubchenko.lab1.utils.Constants.FILE_NAME;

public class NoteFileDao implements NoteDao {

    private static String TAG = NoteFileDao.class.getSimpleName();

    private static Type NOTE_TYPE = new TypeToken<List<Note>>(){}.getType();

    private Context context;

    private Path path;

    private MutableLiveData<List<Note>> notes = new MutableLiveData<>(new ArrayList<>());

    public NoteFileDao(Context context) {
        this.context = context;
        path = Paths.get(context.getFilesDir().getAbsolutePath(), FILE_NAME);
    }

    public LiveData<List<Note>> getAllNotes() {
        checkFileExists();

        ArrayList<Note> notesList;

        try (
                InputStream inputStream = context.openFileInput(FILE_NAME);
                Reader reader = new InputStreamReader(inputStream);
                JsonReader jsonReader = new JsonReader(reader);
        ) {
            jsonReader.beginArray();
            notesList = new Gson().fromJson(jsonReader, NOTE_TYPE);
            if (notesList == null) {
                notesList = new ArrayList<>();
            }
            Log.d(TAG, notesList.toString());
            jsonReader.endArray();
            notesList.forEach(note -> Log.d(TAG, "Parsed " + note.toString()));
            notes.postValue(notesList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Log.d(TAG, notes.toString());

        return notes;
    }

    public LiveData<Note> getNote(long noteId) {
        checkFileExists();
        Note desiredNote = notes.getValue().stream()
                .filter(n -> n.getNoteId() == noteId)
                .findAny()
                .orElse(new Note());

        Log.d(TAG, String.format("getNote(%d): Parsed " +
                desiredNote.toString(), desiredNote.getNoteId()));
        return new MutableLiveData<>(desiredNote);
    }

    public void insert(Note note) {
        try (
                OutputStream outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                Writer writer = new OutputStreamWriter(outputStream);
                JsonWriter jsonWriter = new JsonWriter(writer);
        ) {
            ArrayList<Note> newNotes = new ArrayList<>(notes.getValue());
            note.setNoteId(getNewId());
            newNotes.add(note);
            notes.postValue(newNotes);

            Log.d(TAG, String.format("Saving: %s", note.toString()));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            jsonWriter.beginArray();
            jsonWriter.jsonValue(gson.toJson(notes.getValue()));
            jsonWriter.endArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void insertAll(List<Note> notesToInsert) {
        try (
                InputStream inputStream = context.openFileInput(FILE_NAME);
                Reader reader = new InputStreamReader(inputStream);
                JsonReader jsonReader = new JsonReader(reader);
                OutputStream outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                Writer writer = new OutputStreamWriter(outputStream);
                JsonWriter jsonWriter = new JsonWriter(writer);
        ) {
            ArrayList<Note> newNotes = new ArrayList<>(notes.getValue());
            newNotes.addAll(notesToInsert);

            notes.postValue(newNotes);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            jsonWriter.beginArray();
            jsonWriter.jsonValue(gson.toJson(newNotes));
            jsonWriter.endArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int update(Note note) {
        try (
                OutputStream outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                Writer writer = new OutputStreamWriter(outputStream);
                JsonWriter jsonWriter = new JsonWriter(writer);
        ) {
            Log.d(TAG, "Updating...");
            ArrayList<Note> newNotes = new ArrayList<>(notes.getValue());
            int noteIndex = IntStream.range(0, notes.getValue().size())
                    .filter(i -> note.getNoteId() == notes.getValue().get(i).getNoteId())
                    .findFirst().orElse(-1);
                    //newNotes.stream().(n -> n.getNoteId() == note.getNoteId()) indexOf(note);
            if (noteIndex == -1) {
                Log.d(TAG, String.format("noteIndex = %d, returning", noteIndex));
                return 0;
            }
            newNotes.set(noteIndex, note);
            newNotes.forEach(n -> Log.d(TAG, "Updated " + n.toString()));

            notes.postValue(newNotes);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            jsonWriter.beginArray();
            jsonWriter.jsonValue(gson.toJson(newNotes));
            jsonWriter.endArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return 1;
    }

    public void delete(long noteId) {
        try (
                OutputStream outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                Writer writer = new OutputStreamWriter(outputStream);
                JsonWriter jsonWriter = new JsonWriter(writer);
        ) {
            Log.d(TAG, String.format("delete: %d", noteId));
            ArrayList<Note> newNotes = (ArrayList<Note>) notes.getValue().stream()
                    .filter(n -> n.getNoteId() != noteId)
                    .collect(Collectors.toList());

            if (newNotes.size() == 0) {
                Log.d(TAG, "Remaining size is 0");
            }

            newNotes.forEach(n -> Log.d(TAG, "Remaining " + n.toString()));

            notes.postValue(newNotes);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            jsonWriter.beginArray();
            String json = gson.toJson(newNotes);
            Log.d(TAG, String.format("After delete: %s", json));
            jsonWriter.jsonValue(json);
            jsonWriter.endArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void checkFileExists() {
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private long getNewId() {
        long newId;

        if (notes.getValue().size() == 0) {
            newId = 1;
        } else {
            newId = notes.getValue().stream()
                    .map(Note::getNoteId)
                    .max(Long::compareTo).get() + 1;
        }
        Log.d(TAG, String.format("getNewId: %d", newId));
        return newId;

    }
}
