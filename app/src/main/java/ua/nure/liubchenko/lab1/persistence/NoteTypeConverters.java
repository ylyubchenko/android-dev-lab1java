package ua.nure.liubchenko.lab1.persistence;

import androidx.room.TypeConverter;
import ua.nure.liubchenko.lab1.persistence.Note.Importance;

@SuppressWarnings("WeakerAccess")
public class NoteTypeConverters {
    @TypeConverter
    public static Importance fromInt(int i) {
        return Importance.values()[i];
    }

    @TypeConverter
    public static int fromImportance(Importance im) {
        return im.getValue();
    }
}
