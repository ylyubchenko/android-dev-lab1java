package ua.nure.liubchenko.lab1.data.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import ua.nure.liubchenko.lab1.utils.NoteTypeConverters;

@Entity(tableName = "notes")
public class Note {

    public static DateFormat DATE_FORMAT =
            DateFormat.getDateInstance(DateFormat.SHORT);

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long noteId;

    private String title;

    private String description;

    private long date;

    @TypeConverters(NoteTypeConverters.class)
    private Importance importance;

    private String imagePath;

    @Ignore
    public Note() { }

    public Note(long noteId,
                String title,
                String description,
                long date,
                Importance importance,
                String imagePath) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.importance = importance;
        this.imagePath = imagePath;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() { return date; }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateAsString() {
        return DATE_FORMAT.format(new Date(date));
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public enum Importance {
        LOW(0), NORMAL(1), HIGH(2);

        private final int value;

        Importance(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @NonNull
    @Override
    public String toString() {
        String str = "Note { id = %d, title = %s, desc = %s, importance = %s, date = %s,"
                + " imagePath = %s }";
        String dateFormatted = DATE_FORMAT.format(new Date(date));
        return String.format(Locale.getDefault(), str, noteId, title, description,
                importance.name(), dateFormatted, imagePath);
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
}
