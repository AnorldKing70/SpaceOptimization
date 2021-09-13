package com.example.spaceoptima.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.spaceoptima.R;

public class MainClassesFragment extends Fragment {

    private MainClassesViewModel mainclassesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainclassesViewModel =
                ViewModelProviders.of(this).get(MainClassesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main_classes, container, false);
        final TextView textView = root.findViewById(R.id.txt_main_classes);
        mainclassesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
