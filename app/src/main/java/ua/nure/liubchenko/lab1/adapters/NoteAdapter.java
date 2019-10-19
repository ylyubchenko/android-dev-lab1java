package ua.nure.liubchenko.lab1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ua.nure.liubchenko.lab1.databinding.NoteListItemBinding;
import ua.nure.liubchenko.lab1.persistence.Note;
import ua.nure.liubchenko.lab1.ui.notelist.NoteListFragmentDirections;

public class NoteAdapter extends ListAdapter<Note, NoteViewHolder> {

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(NoteListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
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
        binding.setClickListener(v -> {
            int noteId = binding.getNote().getNoteId();
            navigateToNote(noteId, v);
        });
        this.binding = binding;
    }

    private void navigateToNote(int noteId, View view) {
        NavDirections direction = NoteListFragmentDirections
                .actionNoteListFragmentToNoteDetailsFragment(noteId);
        Navigation.findNavController(view).navigate(direction);
    }

    void bind(Note item) {
        binding.setNote(item);
        binding.executePendingBindings();
    }
}
