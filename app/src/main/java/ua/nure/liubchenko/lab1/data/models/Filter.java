package ua.nure.liubchenko.lab1.data.models;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    private Pattern getPattern(String field) {
        return Pattern.compile(Pattern.quote(field), Pattern.CASE_INSENSITIVE);
    }

    @SuppressWarnings("Convert2Lambda")
    private Predicate<Note> getFieldPredicate(Supplier<String> thisField,
                                              Function<Note, String> compField) {
        return new Predicate<Note>() {
            @Override
            public boolean test(Note note) {
                if (thisField.get() == null) return true;
                Pattern pattern = Filter.this.getPattern(thisField.get());
                Matcher matcher = pattern.matcher(compField.apply(note));
                return matcher.find();
            }
        };
    }

    public List<Note> apply(List<Note> notes) {
        Function<Note, String> importanceSelector = ((Function<Note, Note.Importance>)
                (Note::getImportance)).andThen(Note.Importance::name);

        Predicate<Note> titlePredicate =
                getFieldPredicate(this::getTitle, Note::getTitle);

        Predicate<Note> descriptionPredicate =
                getFieldPredicate(this::getDescription, Note::getDescription);

        Predicate<Note> importancePredicate = importance != null
                ? getFieldPredicate(importance::name, importanceSelector)
                : note -> true;

        return notes.stream()
                .filter(titlePredicate)
                .filter(descriptionPredicate)
                .filter(importancePredicate)
                .collect(Collectors.toList());
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
