package ua.nure.liubchenko.lab1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ua.nure.liubchenko.lab1.NoteDetailsActivity;
import ua.nure.liubchenko.lab1.databinding.NoteListItemBinding;
import ua.nure.liubchenko.lab1.persistence.Note;

public class NoteAdapter extends ListAdapter<Note, NoteViewHolder> {

    private Context context;

    public NoteAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteListItemBinding noteListItemBinding = NoteListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);

        noteListItemBinding.setClickListener(v -> {
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            context.startActivity(intent);
        });

        return new NoteViewHolder(noteListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private static final ItemCallback<Note> DIFF_CALLBACK =
            new ItemCallback<Note>() {

                @Override
                public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                    return oldItem.getNoteId() == newItem.getNoteId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                    return oldItem.equals(newItem);
                }
            };
}

class NoteViewHolder extends RecyclerView.ViewHolder {

    private NoteListItemBinding binding;

    NoteViewHolder(NoteListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(Note item) {
        binding.setNote(item);
        binding.executePendingBindings();
    }
}
