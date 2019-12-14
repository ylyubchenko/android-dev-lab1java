package ua.nure.liubchenko.lab1;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import ua.nure.liubchenko.lab1.databinding.FilterFragmentBinding;
import ua.nure.liubchenko.lab1.viewmodels.FilterViewModel;

public class FilterDialog extends DialogFragment {

    static final String TAG = FilterDialog.class.getSimpleName();

    private FilterViewModel filterViewModel;

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
