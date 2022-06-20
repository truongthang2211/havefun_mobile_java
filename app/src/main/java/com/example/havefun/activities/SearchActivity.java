package com.example.havefun.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havefun.R;
import com.example.havefun.adapters.HomeHotHotelAdapter;
import com.example.havefun.adapters.HotDealSildeAdapter;
import com.example.havefun.adapters.SearchAdapter;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Promotion;
import com.example.havefun.utils.MySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ImageView back_btn;
    private EditText search_input;
    private RecyclerView ListHotelRecyler;
    private SearchAdapter searchAdapter;
    private List<Hotel> ListHotel;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;
        back_btn = findViewById(R.id.search_back_ImgV);
        search_input = findViewById(R.id.search_search_input);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        search_input.requestFocus();
        ListHotelRecyler = findViewById(R.id.search_listHotel_RecyV);
        Intent intent = getIntent();
        String hotelsStr = intent.getStringExtra("listHotel");
        ListHotel = Arrays.asList(new GsonBuilder().create().fromJson(hotelsStr, Hotel[].class));
        searchAdapter = new SearchAdapter(context, ListHotel);
        ListHotelRecyler.setAdapter(searchAdapter);
        ListHotelRecyler.setLayoutManager(new LinearLayoutManager(context));

        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = search_input.getText().toString();
                searchAdapter.getFilter().filter(searchText);
            }
        });
    }
}