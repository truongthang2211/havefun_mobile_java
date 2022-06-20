package com.example.havefun.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havefun.R;
import com.example.havefun.adapters.PromotionSlideAdapter;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Promotion;
import com.example.havefun.utils.MySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailPromotionActivity extends AppCompatActivity {
    Button DetailPro_Viewhotel_btn;
    String HotelURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promotion);
        String ServerURL = getString(R.string.server_address);
        HotelURL = ServerURL + "/api/hotels/";
        Intent intent = getIntent();
        String promoteStr = intent.getStringExtra("promotion");

        Promotion thispro = new Gson().fromJson(promoteStr,Promotion.class);

        ImageView img = findViewById(R.id.DetailPro_img_Img);
        TextView title = findViewById(R.id.DetailPro_title_Tv);
        TextView desc = findViewById(R.id.DetailPro_desc_Tv);
        HotelURL += thispro.getHotel_id();
        Picasso.get().load(thispro.getImg()).into( img);
        title.setText(thispro.getName());
        desc.setText(thispro.getDescription());

        DetailPro_Viewhotel_btn = findViewById(R.id.DetailPro_Viewhotel_btn);

        DetailPro_Viewhotel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartHotelDetailAct();
            }
        });
    }
    private void StartHotelDetailAct(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, HotelURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 200){
                        JSONObject hotel = response.getJSONObject("data");
                        Intent intent = new Intent(DetailPromotionActivity.this, HotelDetailActivity.class);
                        intent.putExtra("hotel",hotel.toString());
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.i("api", error.toString());
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(request);
    }
}