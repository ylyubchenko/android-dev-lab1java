package ua.nure.liubchenko.lab1.data;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class Filter {

    private static DateFormat DATE_FORMAT =
            DateFormat.getDateInstance(DateFormat.SHORT);

    private String title;

    private String description;

    private Long date;

    private Note.Importance importance;

    public Filter(String title, String description, Long date, Note.Importance importance) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.importance = importance;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getDate() {
        return date;
    }

    public Note.Importance getImportance() {
        return importance;
    }

    public List<Note> apply(List<Note> notes) {
        return notes;
    }

    @NonNull
    @Override
    public String toString() {
        String str = "Note { title = %s, desc = %s, importance = %s, date = %s }";
        String titleString = title != null ? title : "";
        String descriptionString = description != null ? description : "";
        String dateString = date != null ? DATE_FORMAT.format(new Date(date)) : "";
        String importanceString = importance != null ? importance.name() : "";
        return String.format(str, titleString, descriptionString, importanceString, dateString);
    }
}
