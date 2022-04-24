package com.example.havefun.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.havefun.R;
import com.example.havefun.adapters.PhotoAdapter;
import com.example.havefun.models.Photo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HotelDetailActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;

    //Tạo button nhập ngày
    Button selectDate ;
    TextView date;
    DatePickerDialog datePickerDialog ;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        mViewPager2 = findViewById(R.id.view_pager_2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator_3);

        //Hàm  nhập ngày
        date  = findViewById(R.id.tvSelectedDate);
        selectDate=findViewById(R.id.btnDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                               calendar = Calendar.getInstance();
                                               year = calendar.get(Calendar.YEAR);
                                               month = calendar.get(Calendar.MONTH);
                                               dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                                               datePickerDialog = new DatePickerDialog(HotelDetailActivity.this,
                                                      new DatePickerDialog.OnDateSetListener() {
                                                          @SuppressLint("SetTextI18n")
                                                          @Override
                                                          public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                                                date.setText(day+"/"+ month+ "/"+ year);
                                                          }
                                                      }, year,
                                                      month,
                                                       dayOfMonth);
                                              datePickerDialog.show();
                                          }
                                      });

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