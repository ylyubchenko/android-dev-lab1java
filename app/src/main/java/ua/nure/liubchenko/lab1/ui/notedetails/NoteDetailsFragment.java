package ua.nure.liubchenko.lab1.ui.notedetails;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.nure.liubchenko.lab1.databinding.NoteDetailsFragmentBinding;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;

public class NoteDetailsFragment extends Fragment {

    public static String TAG = "NOTE_DETAILS";

    public static NoteDetailsFragment newInstance() {
        return new NoteDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        NoteDetailsFragmentBinding binding = NoteDetailsFragmentBinding.inflate(
                inflater, container, false);

        int noteId = NoteDetailsFragmentArgs.fromBundle(getArguments()).getNoteId();

        NoteDetailsViewModelFactory factory =
                InjectorUtils.provideNoteDetailsViewModelFactory(requireActivity(), noteId);

        NoteDetailsViewModel viewModel =
                ViewModelProviders.of(this, factory).get(NoteDetailsViewModel.class);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }
}
