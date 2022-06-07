package com.example.havefun.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.havefun.R;
import com.example.havefun.activities.LoginActivity;
import com.example.havefun.databinding.FragmentProfileBinding;
import com.example.havefun.viewmodels.ProfileViewModel;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        this.context = container.getContext();
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton LogResBtn = view.findViewById(R.id.log_res_btn);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("User", 0);
        String name = pref.getString("userid", "No name defined");
        LogResBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}