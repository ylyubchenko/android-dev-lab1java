package ua.nure.liubchenko.lab1.persistence;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int noteId;

    private String title;

    private String description;

    public Note(int noteId, String title, String description) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Note)) return false;

        Note n = (Note) o;
        return noteId == n.noteId
                && title.equals(n.title)
                && description.equals(n.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                noteId,
                title,
                description
        );
    }
}
