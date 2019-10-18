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

public class NoteDetailsFragment extends Fragment {

    public static NoteDetailsFragment newInstance() {
        return new NoteDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        NoteDetailsViewModel viewModel =
                ViewModelProviders.of(this).get(NoteDetailsViewModel.class);

        NoteDetailsFragmentBinding binding = NoteDetailsFragmentBinding.inflate(
                inflater, container, false);

        return binding.getRoot();
    }
}
