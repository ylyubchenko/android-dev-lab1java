package ua.nure.liubchenko.lab1;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.nure.liubchenko.lab1.data.Filter;
import ua.nure.liubchenko.lab1.databinding.FilterFragmentBinding;
import ua.nure.liubchenko.lab1.data.Note;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;
import ua.nure.liubchenko.lab1.viewmodels.FilterViewModel;
import ua.nure.liubchenko.lab1.viewmodels.FilterViewModelFactory;

public class FilterDialog extends DialogFragment {

    private static final String TAG = FilterDialog.class.getSimpleName();

    private FilterViewModel viewModel;

    private FilterFragmentBinding binding;

    private Consumer<Filter> filterConsumer;

    static FilterDialog withApplicationHandler(Consumer<Filter> filterConsumer) {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.filterConsumer = filterConsumer;
        return filterDialog;
    }

    void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.filter_fragment, container, false);

        FilterViewModelFactory factory =
                InjectorUtils.provideFilterViewModelFactory(getContext());

        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()), factory)
                .get(FilterViewModel.class);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.setViewModel(viewModel);

        binding.getRoot().setOnTouchListener((View v, MotionEvent e) -> {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                v.clearFocus();
                try {
                    InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext())
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                return false;
            }
            v.performClick();
            return false;
        });

        List<String> priorities = Stream.of(Note.Importance.values())
                .map(Note.Importance::name)
                .collect(Collectors.toList());

        ArrayAdapter adapter =
                new ArrayAdapter<>(
                        Objects.requireNonNull(getContext()),
                        R.layout.dropdown_menu_popup_item,
                        priorities);

        binding.filterImportance.setAdapter(adapter);

        binding.filterTitle.addTextChangedListener(provideTextWatcher(viewModel::setTitle));

        binding.filterDesc.addTextChangedListener(provideTextWatcher(viewModel::setDescription));

        binding.filterImportance.addTextChangedListener(provideTextWatcher(viewModel::setImportance));

        binding.clearFilter.setOnClickListener(v -> {
            viewModel.clearFilter();
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.filterToolbar.inflateMenu(R.menu.menu_filter);
        binding.filterToolbar.setNavigationOnClickListener(v -> dismiss());
        binding.filterToolbar.setOnMenuItemClickListener(item -> {
            Log.d(TAG, item.toString());
            filterConsumer.accept(viewModel.getFilter());
            dismiss();
            return true;
        });
    }

    private TextWatcher provideTextWatcher(Consumer<String> setter) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setter.accept(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }
}
