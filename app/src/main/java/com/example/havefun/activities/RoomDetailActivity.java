package com.example.havefun.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havefun.R;
import com.example.havefun.adapters.ImageAdapter;
import com.example.havefun.models.Facility;
import com.example.havefun.models.Promotion;
import com.example.havefun.models.Room;
import com.example.havefun.utils.MySingleton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoomDetailActivity extends AppCompatActivity {

    private TextView tvSubDes,tvRoomTypeBig, tvRoomTypeSmall, tvName, tvRoomPrice, tvFirstHour, tvFirstHourBonus, tvOvernight, tvAllday, tvitem7, tvitem6, tvitem5, tvitem4, tvitem3, tvitem2, tvitem1;
    private ImageView img1, img2, img3, img4, img5, img6,img7;
    private boolean wood_floor;
    private boolean air_conditioning;
    private boolean reception24;
    private boolean wifi;
    private boolean cable_tv;
    private boolean elevator;
    private boolean tv;
    private Context context;
    private ArrayList<String> imgRoomUrls;

    Locale vn = new Locale("vi", "VN");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        tvSubDes = findViewById(R.id.tvRoomDetailSubReview);
        tvRoomTypeBig = findViewById(R.id.tvRoomDetailTypeOfOption);
        tvRoomTypeSmall = findViewById(R.id.tvRoomDetailTypeOfChoose);
        tvName = findViewById(R.id.tvRoomDetailTypeOfRoom);
        tvRoomPrice = findViewById(R.id.tvRoomDetailPriceOfChoose);
        tvFirstHour = findViewById(R.id.tvRoomDetailPriceFirstHour);
        tvFirstHourBonus = findViewById(R.id.tvRoomDetailPriceAddHour);
        tvOvernight = findViewById(R.id.tvRoomDetailPriceOverNight);
        tvAllday = findViewById(R.id.tvRoomDetailPriceOneDay);
        tvitem7 = findViewById(R.id.tvRoomDetailSubFeature7);
        tvitem6 = findViewById(R.id.tvRoomDetailSubFeature6);
        tvitem5 = findViewById(R.id.tvRoomDetailSubFeature5);
        tvitem4 = findViewById(R.id.tvRoomDetailSubFeature4);
        tvitem3 = findViewById(R.id.tvRoomDetailSubFeature3);
        tvitem2 = findViewById(R.id.tvRoomDetailSubFeature2);
        tvitem1 = findViewById(R.id.tvRoomDetailSubFeature1);

        img1 = findViewById(R.id.imgRoomDetailViewIconFeature1);
        img2 = findViewById(R.id.imgRoomDetailViewIconFeature2);
        img3 = findViewById(R.id.imgRoomDetailViewIconFeature3);
        img4 = findViewById(R.id.imgRoomDetailViewIconFeature4);
        img5 = findViewById(R.id.imgRoomDetailViewIconFeature5);
        img6 = findViewById(R.id.imgRoomDetailViewIconFeature6);
        img7 = findViewById(R.id.imgRoomDetailViewIconFeature7);


        context = this;
        ViewPager viewPager = findViewById(R.id.imgRoomDetailViewPager);

        String url = "http://172.16.8.198:3000/api/hotels/k0N8OV6408ddA1ktDSdg";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonDataObject = response.getJSONObject("data");
                    imgRoomUrls = new ArrayList<>();
                    JSONArray jsonRoomArray = jsonDataObject.getJSONArray("rooms");
                    for( int i = 0; i< jsonRoomArray.length(); i++){
                        JSONObject roomItems = jsonRoomArray.getJSONObject(i);


                        JSONArray jsonRoomImgUrl = roomItems.getJSONArray("imgs");

                        for( int m = 0; m < jsonRoomImgUrl.length(); m++) {


                            imgRoomUrls.add(jsonRoomImgUrl.getString(m));
                        }
                        ImageAdapter imageAdapter = new ImageAdapter(context,imgRoomUrls);

                        viewPager.setAdapter(imageAdapter);

                        NumberFormat numberFormatVN = NumberFormat.getCurrencyInstance(vn);


                        String daily_price = roomItems.getString("daily_price");
                        Double d_daily_price = Double.parseDouble(daily_price);
                        daily_price=numberFormatVN.format(d_daily_price);
//                        Log.e("qq",daily_price);
                        String description=roomItems.getString("description");
                        String name = roomItems.getString("name");
                        String room_type =roomItems.getString("room_type");

                        JSONObject facilitiesJSON = roomItems.getJSONObject("facilities");

                        wood_floor = facilitiesJSON.getBoolean("wood_floor");
                        air_conditioning = facilitiesJSON.getBoolean("air_conditioning");
                        reception24 = facilitiesJSON.getBoolean("reception24");
                        wifi = facilitiesJSON.getBoolean("wifi");
                        cable_tv=facilitiesJSON.getBoolean("cable_tv");
                        elevator = facilitiesJSON.getBoolean("elevator");
                        tv = facilitiesJSON.getBoolean("tv");


                        String hour_price = roomItems.getString("hour_price");
                        Double d_hour_price = Double.parseDouble(hour_price);
                        hour_price=numberFormatVN.format(d_hour_price);
                        String hour_price_bonus = roomItems.getString("hour_price_bonus");
                        Double d_hour_price_bonus = Double.parseDouble(hour_price_bonus);
                        hour_price_bonus=numberFormatVN.format(d_hour_price_bonus);
                        String overnight_price = roomItems.getString("overnight_price");
                        Double d_overnight_price = Double.parseDouble(overnight_price);
                        overnight_price=numberFormatVN.format(d_overnight_price);


                        tvSubDes.setText(description);
                        tvRoomTypeBig.setText(room_type);
                        tvRoomTypeSmall.setText(room_type);
                        tvName.setText(name);
                        tvRoomPrice.setText(daily_price);
                        tvFirstHour.setText(hour_price);
                        tvFirstHourBonus.setText(hour_price_bonus);
                        tvOvernight.setText(overnight_price);
                        tvAllday.setText(daily_price);

                        if(wood_floor){
                            img1.setVisibility(View.VISIBLE);
                            tvitem1.setVisibility(TextView.VISIBLE);
                        }else{
                            img1.setVisibility(View.INVISIBLE);
                            tvitem1.setVisibility(TextView.GONE);
                        }

                        if(wifi){
                            img2.setVisibility(View.VISIBLE);
                            tvitem2.setVisibility(TextView.VISIBLE);
                        }else{
                            img2.setVisibility(View.INVISIBLE);
                            tvitem2.setVisibility(TextView.GONE);
                        }

                        if(reception24){
                            img3.setVisibility(View.VISIBLE);
                            tvitem3.setVisibility(TextView.VISIBLE);
                        }else{
                            img3.setVisibility(View.INVISIBLE);
                            tvitem3.setVisibility(TextView.GONE);
                        }

                        if(air_conditioning){
                            img4.setVisibility(View.VISIBLE);
                            tvitem4.setVisibility(TextView.VISIBLE);
                        }else{
                            img4.setVisibility(View.INVISIBLE);
                            tvitem4.setVisibility(TextView.GONE);
                        }

                        if(tv){
                            img5.setVisibility(View.VISIBLE);
                            tvitem5.setVisibility(TextView.VISIBLE);
                        }else{
                            img5.setVisibility(View.INVISIBLE);
                            tvitem5.setVisibility(TextView.GONE);
                        }
//
                        if(elevator){
                            img6.setVisibility(View.VISIBLE);
                            tvitem6.setVisibility(TextView.VISIBLE);

                        }else{
                            img6.setVisibility(View.INVISIBLE);
                            tvitem6.setVisibility(TextView.GONE);
                        }
//
                        if(cable_tv){
                            img7.setVisibility(View.VISIBLE);
                            tvitem7.setVisibility(TextView.VISIBLE);
                        }else{
                            img7.setVisibility(View.INVISIBLE);
                            tvitem7.setVisibility(TextView.GONE);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("GG",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("GGG",error.toString());
            }
        });

        MySingleton.getInstance(RoomDetailActivity.this).addToRequestQueue(request);


    }
}