package ua.nure.liubchenko.lab1;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import ua.nure.liubchenko.lab1.databinding.FilterFragmentBinding;
import ua.nure.liubchenko.lab1.utils.InjectorUtils;
import ua.nure.liubchenko.lab1.viewmodels.FilterViewModel;
import ua.nure.liubchenko.lab1.viewmodels.FilterViewModelFactory;

public class FilterDialog extends DialogFragment {

    static final String TAG = FilterDialog.class.getSimpleName();

    private FilterViewModel viewModel;

    private FilterFragmentBinding binding;

    static FilterDialog show(FragmentManager fragmentManager) {
        FilterDialog exampleDialog = new FilterDialog();
        exampleDialog.show(fragmentManager, TAG);
        return exampleDialog;
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
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.filter_fragment, container, false);

        FilterViewModelFactory factory =
                InjectorUtils.provideFilterViewModelFactory(getContext());

        viewModel = new ViewModelProvider(this, factory).get(FilterViewModel.class);

        binding.getRoot().setOnTouchListener((View v, MotionEvent e) -> {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                v.clearFocus();
                try {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                return false;
            }
            v.performClick();
            return false;
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        binding.filterToolbar.inflateMenu(R.menu.menu_filter);
        binding.filterToolbar.setNavigationOnClickListener(v -> dismiss());
        binding.filterToolbar.setOnMenuItemClickListener(item -> {
            Log.d(TAG, item.toString());
            dismiss();
            return true;
        });
    }
}
