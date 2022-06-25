package com.example.havefun.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.havefun.models.Timestamp;
import com.example.havefun.utils.MySingleton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RoomDetailActivity extends AppCompatActivity {

    private TextView tvSubDes, tvRoomTypeBig, tvRoomTypeSmall, tvName, tvRoomPrice, tvFirstHour, tvFirstHourBonus, tvOvernight, tvAllday, tvitem7, tvitem6, tvitem5, tvitem4, tvitem3, tvitem2, tvitem1;
    private ImageView img1, img2, img3, img4, img5, img6, img7;
    private boolean wood_floor;
    private boolean air_conditioning;
    private boolean reception24;
    private boolean wifi;
    private boolean cable_tv;
    private boolean elevator;
    private boolean tv;
    private Context context;
    private ArrayList<String> imgRoomUrls;
    private Timestamp time_start, time_stop;
    private Room currentRoom;
    String hotelID,order_type;

    Locale vn = new Locale("vi", "VN");

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        String ServerAddres = getString(R.string.server_address);
        Intent intent = getIntent();
        String timeStartString = intent.getStringExtra("time_start");
        String timeStopString = intent.getStringExtra("time_stop");
        String roomStr = intent.getStringExtra("room");
        String price = intent.getStringExtra("price");
        hotelID = intent.getStringExtra("hotel_id");
        order_type = intent.getStringExtra("order_type");
        time_start = new Gson().fromJson(timeStartString, Timestamp.class);
        time_stop = new Gson().fromJson(timeStopString, Timestamp.class);
        currentRoom = new Gson().fromJson(roomStr, Room.class);


        Button orderRoom = findViewById(R.id.btnRoomDetailCheckOut);
        orderRoom.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String createOrderUrl = ServerAddres + "/api/orders/create";
                SharedPreferences pref = getApplicationContext().getSharedPreferences("User", 0);
                String name = pref.getString("userObject", "undefined");
                if (!name.equals("undefined")) {
                    try {
                        JSONObject userObj = new JSONObject(name);
                        JSONObject createOrder = new JSONObject();
                        createOrder.put("hotelID", hotelID);
                        createOrder.put("roomID", currentRoom.getId());
                        createOrder.put("userID", userObj.getString("id"));
                        createOrder.put("order_type", order_type);

                        createOrder.put("order_start",new JSONObject(new Gson().toJson(time_start)));
                        createOrder.put("order_end",new JSONObject(new Gson().toJson(time_stop)));
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, createOrderUrl, createOrder, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getInt("status") == 200) {
                                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Thành công")
                                                .setContentText("Bạn đã đặt phòng thành công")
                                                .show();
                                    } else {
                                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Có lỗi xảy ra, hãy thử lại")
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Log.e("GGG", error.toString());
                            }
                        });

                        MySingleton.getInstance(RoomDetailActivity.this).addToRequestQueue(request);
                    } catch (JSONException e) {
                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Có lỗi xảy ra, hãy thử đăng xuất và đăng nhập lại")
                                .show();
                    }

                } else {
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Bạn cần đăng nhập để thực hiện đặt phòng")
                            .show();
                }
            }
        });

        ViewPager viewPager = findViewById(R.id.imgRoomDetailViewPager);

        ImageAdapter imageAdapter = new ImageAdapter(context, new ArrayList<String>(Arrays.asList(currentRoom.getImgs())));

        viewPager.setAdapter(imageAdapter);

        NumberFormat numberFormatVN = NumberFormat.getCurrencyInstance(vn);


        String daily_price = numberFormatVN.format(currentRoom.getDaily_price());
        String hour_price_bonus = numberFormatVN.format(currentRoom.getHour_price_bonus());
        String overnight_price = numberFormatVN.format(currentRoom.getOvernight_price());
        String hour_price = numberFormatVN.format(currentRoom.getHour_price());

        TextView tvRoomDetailTimeCheckIn = findViewById(R.id.tvRoomDetailTimeCheckIn);
        TextView tvRoomDetailTimeCheckOut = findViewById(R.id.tvRoomDetailTimeCheckOut);

        TextView tvRoomDetailDayCheckIn = findViewById(R.id.tvRoomDetailDayCheckIn);
        TextView tvRoomDetailDayCheckOut = findViewById(R.id.tvRoomDetailDayCheckOut);

        tvRoomDetailTimeCheckIn.setText(time_start.toString().split(" ")[1]);
        tvRoomDetailTimeCheckOut.setText(time_stop.toString().split(" ")[1]);
        tvRoomDetailDayCheckIn.setText(time_start.toString().split(" ")[0]);
        tvRoomDetailDayCheckOut.setText(time_stop.toString().split(" ")[0]);

        tvSubDes.setText(currentRoom.getDescription());
        tvRoomTypeBig.setText(currentRoom.getRoom_type());
        tvRoomTypeSmall.setText(currentRoom.getRoom_type());
        tvName.setText(currentRoom.getName());
        tvRoomPrice.setText(price);
        tvFirstHour.setText(hour_price);
        tvFirstHourBonus.setText(hour_price_bonus);
        tvOvernight.setText(overnight_price);
        tvAllday.setText(daily_price);

        if (currentRoom.getFacilities().isWood_floor()) {
            img1.setVisibility(View.VISIBLE);
            tvitem1.setVisibility(TextView.VISIBLE);
        } else {
            img1.setVisibility(View.INVISIBLE);
            tvitem1.setVisibility(TextView.GONE);
        }

        if (currentRoom.getFacilities().isWifi()) {
            img2.setVisibility(View.VISIBLE);
            tvitem2.setVisibility(TextView.VISIBLE);
        } else {
            img2.setVisibility(View.INVISIBLE);
            tvitem2.setVisibility(TextView.GONE);
        }

        if (currentRoom.getFacilities().isReception24()) {
            img3.setVisibility(View.VISIBLE);
            tvitem3.setVisibility(TextView.VISIBLE);
        } else {
            img3.setVisibility(View.INVISIBLE);
            tvitem3.setVisibility(TextView.GONE);
        }

        if (currentRoom.getFacilities().isAir_conditioning()) {
            img4.setVisibility(View.VISIBLE);
            tvitem4.setVisibility(TextView.VISIBLE);
        } else {
            img4.setVisibility(View.INVISIBLE);
            tvitem4.setVisibility(TextView.GONE);
        }

        if (currentRoom.getFacilities().isTv()) {
            img5.setVisibility(View.VISIBLE);
            tvitem5.setVisibility(TextView.VISIBLE);
        } else {
            img5.setVisibility(View.INVISIBLE);
            tvitem5.setVisibility(TextView.GONE);
        }
//
        if (currentRoom.getFacilities().isElevator()) {
            img6.setVisibility(View.VISIBLE);
            tvitem6.setVisibility(TextView.VISIBLE);

        } else {
            img6.setVisibility(View.INVISIBLE);
            tvitem6.setVisibility(TextView.GONE);
        }
//
        if (currentRoom.getFacilities().isCable_tv()) {
            img7.setVisibility(View.VISIBLE);
            tvitem7.setVisibility(TextView.VISIBLE);
        } else {
            img7.setVisibility(View.INVISIBLE);
            tvitem7.setVisibility(TextView.GONE);
        }


    }
}