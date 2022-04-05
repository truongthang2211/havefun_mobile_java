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
import com.example.havefun.viewmodels.PromotionViewModel;

public class PromotionFragment extends Fragment {

    private FragmentPreferenceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PromotionViewModel promotionViewModel =
                new ViewModelProvider(this).get(PromotionViewModel.class);

        binding = FragmentPreferenceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        promotionViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}