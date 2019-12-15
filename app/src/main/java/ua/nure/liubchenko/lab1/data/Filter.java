package ua.nure.liubchenko.lab1.data;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;

public class Filter {

    private static DateFormat DATE_FORMAT =
            DateFormat.getDateInstance(DateFormat.SHORT);

    private String title;

    private String description;

    private long date;

    private Note.Importance importance;

    public Filter(String title, String description, long date, Note.Importance importance) {
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

    public long getDate() {
        return date;
    }

    public Note.Importance getImportance() {
        return importance;
    }

    @NonNull
    @Override
    public String toString() {
        String str = "Note { title = %s, desc = %s, importance = %s, date = %s }";
        String dateFormatted = DATE_FORMAT.format(new Date(date));
        String importanceFormatted = Optional.ofNullable(importance).map(Note.Importance::name).orElse("");
        return String.format(str, title, description, importanceFormatted, dateFormatted);
    }
}
