package ua.nure.liubchenko.lab1.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ua.nure.liubchenko.lab1.ShowNoteActivity;
import ua.nure.liubchenko.lab1.databinding.NoteListItemBinding;
import ua.nure.liubchenko.lab1.persistence.Note;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {

    private static String TAG = NoteAdapter.class.getSimpleName();

    private Context context;

    public NoteAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
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

    class NoteViewHolder extends RecyclerView.ViewHolder implements OnCreateContextMenuListener {

        private NoteListItemBinding binding;

        NoteViewHolder(NoteListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Note item) {
            binding.setNote(item);
            binding.getRoot().setOnCreateContextMenuListener(this);
            binding.setClickListener(v -> {
                context.startActivity(new Intent(context, ShowNoteActivity.class)
                        .setAction(Intent.ACTION_EDIT)
                        .putExtra(Note.class.getName(), item.getNoteId()));
            });
            binding.executePendingBindings();
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Note actions");
            menu.add(Menu.NONE, 1, 1, "Edit")
                    .setOnMenuItemClickListener(onEditMenu);
            menu.add(Menu.NONE, 2, 2, "Delete")
                    .setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = item -> {
            switch (item.getItemId()) {
                case 1:
                    Log.d(TAG, "Edit");
                    break;
                case 2:
                    Log.d(TAG, "Delete");
                    break;
            }
            return true;
        };

    }
}




