package com.example.havefun.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;


import com.example.havefun.R;
import com.example.havefun.adapters.ImageAdapter;

public class RoomDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        ViewPager viewPager = findViewById(R.id.imgViewPager);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        viewPager.setAdapter(imageAdapter);
    }
}