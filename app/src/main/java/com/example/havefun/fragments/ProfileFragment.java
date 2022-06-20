package com.example.havefun.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havefun.R;
import com.example.havefun.activities.LoginActivity;
import com.example.havefun.activities.MyOrderActivity;
import com.example.havefun.databinding.FragmentProfileBinding;
import com.example.havefun.models.Promotion;
import com.example.havefun.utils.MySingleton;
import com.example.havefun.viewmodels.ProfileViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Context context;
    private Fragment thisFragment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        this.context = container.getContext();
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        thisFragment=this;
//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton LogResBtn = view.findViewById(R.id.log_res_btn);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("User", 0);
        String name = pref.getString("userObject", "undefined");

        TextView email = view.findViewById(R.id.usermail_txt);
        TextView kmai_txt = view.findViewById(R.id.kmai_txt);
        MaterialButton log_res_btn = view.findViewById(R.id.log_res_btn);
        Button order_btn = view.findViewById(R.id.order_btn);
        Button logout_btn = view.findViewById(R.id.logout_btn);
        if (!name.equals("undefined")){
            try {

                JSONObject userObj = new JSONObject(name);
                email.setText(userObj.getString("email"));
                kmai_txt.setVisibility(View.GONE);
                log_res_btn.setVisibility(View.GONE);
                logout_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pref.edit().remove("userObject").commit();
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }
                });
            } catch (JSONException e) {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Có lỗi xảy ra, hãy thử đăng xuất và đăng nhập lại")
                        .show();
            }

        }else{
            order_btn.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            logout_btn.setVisibility(View.GONE);
        }
        LogResBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,3);
            }
        });
        Button Myorders = view.findViewById(R.id.order_btn);
        Myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3) {
            String result=data.getStringExtra("result");
            if (result.equals("login")) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","login");
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        }
    }
}