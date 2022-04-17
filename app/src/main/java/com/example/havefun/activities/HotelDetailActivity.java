package com.example.havefun.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.havefun.R;
import com.example.havefun.adapters.PhotoAdapter;
import com.example.havefun.models.Photo;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HotelDetailActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        mViewPager2 = findViewById(R.id.view_pager_2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator_3);

        PhotoAdapter photoAdapter = new PhotoAdapter(this,getListPhoto());
        mViewPager2.setAdapter(photoAdapter);
        mCircleIndicator3.setViewPager(mViewPager2);
        ConstraintLayout room_item = findViewById(R.id.detailhotel_contrain_roomitem);
        room_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotelDetailActivity.this,RoomDetailActivity.class);
                startActivity(intent);
            }
        });

    }
    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.victory1));
        list.add(new Photo(R.drawable.victory));
        list.add(new Photo(R.drawable.victory2));

        return list;
    }
}