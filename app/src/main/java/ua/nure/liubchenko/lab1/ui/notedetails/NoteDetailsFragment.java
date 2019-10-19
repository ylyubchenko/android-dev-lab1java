package ua.nure.liubchenko.lab1.ui.notedetails;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.nure.liubchenko.lab1.R;
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
        int noteId = NoteDetailsFragmentArgs.fromBundle(getArguments()).getNoteId();

        Log.i(TAG, String.format("noteId = %d", noteId));

        NoteDetailsViewModelFactory factory =
                InjectorUtils.provideNoteDetailsViewModelFactory(requireActivity(), noteId);

        NoteDetailsFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.note_details_fragment, container, false);
//                NoteDetailsFragmentBinding.inflate(
//                inflater, container, false);

        NoteDetailsViewModel viewModel =
                ViewModelProviders.of(this, factory).get(NoteDetailsViewModel.class);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.setViewModel(viewModel);

        viewModel.getNote().observe(getViewLifecycleOwner(), note ->
                Log.i(TAG, note.toString()));

        return binding.getRoot();
    }
}
