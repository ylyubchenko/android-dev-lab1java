package ua.nure.liubchenko.lab1.ui.notelist;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.nure.liubchenko.lab1.adapters.NoteAdapter;
import ua.nure.liubchenko.lab1.databinding.NoteListFragmentBinding;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;

public class NoteListFragment extends Fragment {

    public static NoteListFragment newInstance() {
        return new NoteListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        NoteListViewModelFactory factory =
                InjectorUtils.provideNoteListViewModelFactory(requireContext());

        NoteListViewModel viewModel =
                ViewModelProviders.of(this, factory).get(NoteListViewModel.class);

        NoteListFragmentBinding binding = NoteListFragmentBinding.inflate(
                inflater, container, false);

        NoteAdapter adapter = new NoteAdapter();

        viewModel.getAllNotes().observe(getViewLifecycleOwner(), adapter::submitList);

        binding.noteList.setAdapter(adapter);

        return binding.getRoot();
    }

}
