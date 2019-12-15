package ua.nure.liubchenko.lab1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import ua.nure.liubchenko.lab1.ShowNoteActivity;
import ua.nure.liubchenko.lab1.databinding.NoteListItemBinding;
import ua.nure.liubchenko.lab1.data.Note;
import ua.nure.liubchenko.lab1.data.NoteRepository;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private static String TAG = NoteAdapter.class.getSimpleName();

    private Context context;

    private NoteRepository noteRepository;

    private List<Note> notes;

    public NoteAdapter(Context context) {
        this.context = context;
        noteRepository = InjectorUtils.getNoteRepository(context);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getNoteId();
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
        holder.bind(position, notes.get(position));
        holder.binding.card.setActivated(true);
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    class NoteViewHolder extends ViewHolder {

        private NoteListItemBinding binding;

        NoteViewHolder(NoteListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position, Note note) {
            binding.setNote(note);
            binding.card.setOnLongClickListener(v -> {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Note deletion")
                        .setMessage(String.format("Are you sure you want to delete note " +
                                "with title \"%s\"?", note.getTitle()))
                        .setPositiveButton("Yes", (dialog, which) -> {
                            noteRepository.delete(note);
                            NoteAdapter.this.notifyItemRemoved(position);
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            });
            binding.card.setOnClickListener(v ->
                context.startActivity(new Intent(context, ShowNoteActivity.class)
                        .setAction(Intent.ACTION_EDIT)
                        .putExtra(Note.class.getName(), note.getNoteId())));
            binding.executePendingBindings();
        }
    }
}
