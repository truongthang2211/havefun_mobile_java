package com.example.havefun.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.havefun.databinding.FragmentPreferenceBinding;
import com.example.havefun.viewmodels.PreferenceViewModel;

public class PreferenceFragment extends Fragment {

    private FragmentPreferenceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PreferenceViewModel preferenceViewModel =
                new ViewModelProvider(this).get(PreferenceViewModel.class);

        binding = FragmentPreferenceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        preferenceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}