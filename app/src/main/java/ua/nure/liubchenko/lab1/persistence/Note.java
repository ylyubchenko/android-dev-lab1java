package ua.nure.liubchenko.lab1.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Entity(tableName = "notes")
public class Note {

    private static DateFormat DATE_FORMAT =
            DateFormat.getDateInstance(DateFormat.SHORT);

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int noteId;

    private String title;

    private String description;

    private long date;

    //private Importance importance;

    public Note(int noteId, String title, String description, long date) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getDate() { return date; }

    public String getDateAsString() {
        return DATE_FORMAT.format(new Date(date));
    }

//    public Importance getImportance() {
//        return importance;
//    }

    @NonNull
    @Override
    public String toString() {
        String str = "Note { id = %d, title = %s, desc = %s, date = %s }";
        String dateFormatted = DATE_FORMAT.format(new Date(date));
        return String.format(Locale.getDefault(), str, noteId, title, description, dateFormatted);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Note)) return false;

        Note n = (Note) o;
        return noteId == n.noteId
                && title.equals(n.title)
                && description.equals(n.description)
                && date == n.date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                noteId,
                title,
                description,
                date
        );
    }

    public enum Importance {
        LOW,
        NORMAL,
        HIGH,
    }
}
