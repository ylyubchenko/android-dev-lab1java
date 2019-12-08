package ua.nure.liubchenko.lab1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import ua.nure.liubchenko.lab1.ShowNoteActivity;
import ua.nure.liubchenko.lab1.databinding.NoteListItemBinding;
import ua.nure.liubchenko.lab1.persistence.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private static String TAG = NoteAdapter.class.getSimpleName();

    private Context context;

    private List<Note> notes;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteListItemBinding binding = NoteListItemBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        this.notifyDataSetChanged();
    }

    class NoteViewHolder extends ViewHolder {

        private NoteListItemBinding binding;

        NoteViewHolder(NoteListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Note item) {
            binding.setNote(item);
            binding.setClickListener(v ->
                context.startActivity(new Intent(context, ShowNoteActivity.class)
                        .setAction(Intent.ACTION_EDIT)
                        .putExtra(Note.class.getName(), item.getNoteId())));
            binding.executePendingBindings();
        }
    }
}
