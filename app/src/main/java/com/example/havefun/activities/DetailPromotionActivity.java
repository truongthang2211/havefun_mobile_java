package com.example.havefun.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.havefun.R;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Promotion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class DetailPromotionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promotion);

        Intent intent = getIntent();
        String promoteStr = intent.getStringExtra("promotion");

        Promotion thispro = new Gson().fromJson(promoteStr,Promotion.class);

        ImageView img = findViewById(R.id.DetailPro_img_Img);
        TextView title = findViewById(R.id.DetailPro_title_Tv);
        TextView desc = findViewById(R.id.DetailPro_desc_Tv);

        Picasso.get().load(thispro.getImg()).into( img);
        title.setText(thispro.getName());
        desc.setText(thispro.getDescription());
    }
}