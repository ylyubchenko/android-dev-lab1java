package ua.nure.liubchenko.lab1.utils;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;

import androidx.core.widget.ImageViewCompat;
import androidx.databinding.BindingAdapter;

import ua.nure.liubchenko.lab1.R;
import ua.nure.liubchenko.lab1.data.Note;

public class NoteBindingAdapters {

    @BindingAdapter("importance")
    public static void setImportance(View view, Note.Importance importance) {
        switch (importance) {
            case LOW: {
                int color = view.getContext().getColor(R.color.importanceLow);
                ImageViewCompat.setImageTintList((ImageView) view, ColorStateList.valueOf(color));
                break;
            }
            case NORMAL: {
                int color = view.getContext().getColor(R.color.importanceNormal);
                ImageViewCompat.setImageTintList((ImageView) view, ColorStateList.valueOf(color));
                break;
            }
            case HIGH: {
                int color = view.getContext().getColor(R.color.importanceHigh);
                ImageViewCompat.setImageTintList((ImageView) view, ColorStateList.valueOf(color));
                break;
            }
        }
    }
}
