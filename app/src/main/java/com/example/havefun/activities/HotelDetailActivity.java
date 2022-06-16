package com.example.havefun.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.havefun.R;
import com.example.havefun.adapters.PhotoAdapter;
import com.example.havefun.models.Location;
import com.example.havefun.models.Photo;
import com.example.havefun.models.Rating;
import com.example.havefun.utils.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator3;

public class HotelDetailActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;


    private TextView tvName,tvRoomName, tvRoomType, tvStart, tvStartComment, tvComment, tvOvernightPrice, tvDescription, tvLocation,tvitem1,tvitem2,tvitem3,tvitem4,tvitem5,tvitem6,tvitem7;
    private ImageView img1, img2, img3, img4, img5, img6,img7;
    private boolean wood_floor;
    private boolean air_conditioning;
    private boolean reception24;
    private boolean wifi;
    private boolean cable_tv;
    private boolean elevator;
    private boolean tv;
    private Context context;
    int hourStart, minuStart, hourStop, minuStop;

    private Button btnTheoGio, btnQuaDem, btnTheoNgay, btnNhanPhong, btnTraPhong;
    private TextView selectDateOrTimeStart, selectDateOrTimeStop;

    //Tạo button nhập ngày
    Button selectDate;
    TextView date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;


    java.util.Date value;
    final Calendar myCalendar= Calendar.getInstance();
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    java.util.Date timeDateStart;
    java.util.Date timeDateStop;

    long lTimeStart, lTimeStop;
    java.sql.Timestamp timestampStart, timestampStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        mViewPager2 = findViewById(R.id.view_pager_2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator_3);

        tvName = findViewById(R.id.textView);
        tvStart = findViewById(R.id.txt_star);
        tvStartComment = findViewById(R.id.tv_LuotBinhLuan);

        tvLocation = findViewById(R.id.textView3);
        tvComment = findViewById(R.id.nd_BinhLuan);
        tvDescription = findViewById(R.id.txt_ndGT);
        tvOvernightPrice = findViewById(R.id.textView10);
        tvRoomType = findViewById(R.id.textView9);
        tvRoomName = findViewById(R.id.textView6);

        btnTheoGio =findViewById(R.id.btn_Theogio);
        btnQuaDem = findViewById(R.id.btn_Quadem);
        btnTheoNgay = findViewById(R.id.btn_Theongay);
        btnNhanPhong = findViewById(R.id.btnDate);
        btnTraPhong = findViewById(R.id.btnDate1);
        selectDateOrTimeStart = findViewById(R.id.tvSelectedDate);
        selectDateOrTimeStop = findViewById(R.id.tvSelectedDate1);

        tvitem7 = findViewById(R.id.tvHotelDetailSubFeature7);
        tvitem6 = findViewById(R.id.tvHotelDetailSubFeature6);
        tvitem5 = findViewById(R.id.tvHotelDetailSubFeature5);
        tvitem4 = findViewById(R.id.tvHotelDetailSubFeature4);
        tvitem3 = findViewById(R.id.tvHotelDetailSubFeature3);
        tvitem2 = findViewById(R.id.tvHotelDetailSubFeature2);
        tvitem1 = findViewById(R.id.tvHotelDetailSubFeature1);

        img1 = findViewById(R.id.imgHotelDetailViewIconFeature1);
        img2 = findViewById(R.id.imgHotelDetailViewIconFeature2);
        img3 = findViewById(R.id.imgHotelDetailViewIconFeature3);
        img4 = findViewById(R.id.imgHotelDetailViewIconFeature4);
        img5 = findViewById(R.id.imgHotelDetailViewIconFeature5);
        img6 = findViewById(R.id.imgHotelDetailViewIconFeature6);
        img7 = findViewById(R.id.imgHotelDetailViewIconFeature7);

        String ServerAddres = getString(R.string.server_address);

        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hourStart = hourOfDay;
                minuStart = minute;
            }
        };
        TimePickerDialog.OnTimeSetListener mTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hourStop = hourOfDay;
                minuStop = minute;
            }
        };

        TimePickerDialog.OnTimeSetListener mTimeSetListener0_1 = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hourStart = hourOfDay;
                minuStart = minute;
            }
        };

        DatePickerDialog.OnDateSetListener date3 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                myCalendar.set(Calendar.MINUTE,00);
                myCalendar.set(Calendar.HOUR_OF_DAY,myCalendar.get(Calendar.HOUR_OF_DAY) + 1);
                myCalendar.set(Calendar.SECOND,00);


                updateLabel3();
            }
        };
        DatePickerDialog.OnDateSetListener date3_1 =new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
//                myCalendar.set(Calendar.MINUTE,00);
//                myCalendar.set(Calendar.HOUR_OF_DAY,myCalendar.get(Calendar.HOUR_OF_DAY) + 1);
//                myCalendar.set(Calendar.SECOND,00);


                updateLabel3_1();

            }
        };
        DatePickerDialog.OnDateSetListener date3_2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
//                myCalendar.set(Calendar.MINUTE,00);
//                myCalendar.set(Calendar.HOUR_OF_DAY,myCalendar.get(Calendar.HOUR_OF_DAY) + 1);
//                myCalendar.set(Calendar.SECOND,00);


                updateLabel3_2();
            }
        };

        btnTheoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new DatePickerDialog(HotelDetailActivity.this,date3,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                btnNhanPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//
//                        new TimePickerDialog(HotelDetailActivity.this, mTimeSetListener, hourStart, minuStart, true).show();

                        new DatePickerDialog(HotelDetailActivity.this,date3_1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        new TimePickerDialog(HotelDetailActivity.this, mTimeSetListener, hourStart, minuStart, true).show();

                    }
                });
                btnTraPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        new TimePickerDialog(HotelDetailActivity.this, mTimeSetListener1, hourStop, minuStop, true).show();
                        new DatePickerDialog(HotelDetailActivity.this,date3_2,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        new TimePickerDialog(HotelDetailActivity.this, mTimeSetListener1, hourStop, minuStop, true).show();

                    }
                });

            }


        });
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                myCalendar.set(Calendar.MINUTE,00);
                myCalendar.set(Calendar.HOUR_OF_DAY,12);
                myCalendar.set(Calendar.SECOND,00);


                updateLabel();
            }
        };

        DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                myCalendar.set(Calendar.MINUTE,00);
                myCalendar.set(Calendar.HOUR_OF_DAY,21);
                myCalendar.set(Calendar.SECOND,00);


                updateLabel1();
            }
        };

        DatePickerDialog.OnDateSetListener date1_1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                myCalendar.set(Calendar.MINUTE,00);
                myCalendar.set(Calendar.HOUR_OF_DAY,21);
                myCalendar.set(Calendar.SECOND,00);


                updateLabel4_1();
            }
        };



        btnTheoNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(HotelDetailActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                btnNhanPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(HotelDetailActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                btnTraPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(HotelDetailActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        });


        btnQuaDem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(HotelDetailActivity.this,date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                btnNhanPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(HotelDetailActivity.this,date1_1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        new TimePickerDialog(HotelDetailActivity.this, mTimeSetListener0_1, hourStart, minuStart, true).show();
                    }
                });
                btnTraPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(HotelDetailActivity.this,date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        });




/*        selectDate.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               jsonParse();
           }
        });*/

        RequestQueue requestqueue = Volley.newRequestQueue(this);

        /*String url = "http://192.168.1.2:3000/api/hotels";*/
        String url = ServerAddres+"/api/hotels";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsondataArray = response.getJSONArray("data");

                            for(int i=0; i<jsondataArray.length();i++) {
                                JSONObject hotelItem = jsondataArray.getJSONObject(i);

                                String name = hotelItem.getString("name");
                                String description = hotelItem.getString("description");

                                JSONArray jsonRatingArray = hotelItem.getJSONArray("ratings");
                                for(int m =0;m<jsonRatingArray.length();m++) {
                                    JSONObject ratingsItem = jsonRatingArray.getJSONObject(m);
                                    String ratings = ratingsItem.getString("start");
                                    String comment = ratingsItem.getString("comment");

                                    tvComment.setText(comment);
                                    tvStart.setText(ratings);
                                    tvStartComment.setText(ratings);
                                }

                                JSONObject jsonLocationObject = hotelItem.getJSONObject("location");
                                String address = jsonLocationObject.getString("address");
                                String village = jsonLocationObject.getString("village");
                                String district = jsonLocationObject.getString("district");
                                String city = jsonLocationObject.getString("city");

                                tvLocation.append(address + ',' + village + ',' + district + ',' + city);

                                JSONArray jsonRoomArray = hotelItem.getJSONArray("rooms");
                                JSONObject roomItems = jsonRoomArray.getJSONObject(0);
                                for (int j=0;j<jsonRoomArray.length();j++) {
                                    JSONObject roomItem = jsonRoomArray.getJSONObject(j);

                                    String RoomName = roomItem.getString("name");
                                    String RoomType = roomItem.getString("room_type");
                                    String OvernightPrice = roomItem.getString("overnight_price");

                                    tvName.setText(name);
                                    tvDescription.setText(description);
                                    tvRoomName.setText(RoomName);
                                    tvRoomType.setText(RoomType);
                                    tvOvernightPrice.setText(OvernightPrice);

                                }
                                JSONObject facilitiesJSON = roomItems.getJSONObject("facilities");

                                wood_floor = facilitiesJSON.getBoolean("wood_floor");
                                air_conditioning = facilitiesJSON.getBoolean("air_conditioning");
                                reception24 = facilitiesJSON.getBoolean("reception24");
                                wifi = facilitiesJSON.getBoolean("wifi");
                                cable_tv=facilitiesJSON.getBoolean("cable_tv");
                                elevator = facilitiesJSON.getBoolean("elevator");
                                tv = facilitiesJSON.getBoolean("tv");

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
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Error", error.toString());
            }
        });

        requestqueue.add(request);


        //Hàm  nhập ngày
//        date = findViewById(R.id.tvSelectedDate);
//        selectDate = findViewById(R.id.btnDate);
//        selectDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calendar = Calendar.getInstance();
//                year = calendar.get(Calendar.YEAR);
//                month = calendar.get(Calendar.MONTH);
//                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//
//                datePickerDialog = new DatePickerDialog(HotelDetailActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @SuppressLint("SetTextI18n")
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                date.setText(day + "/" + month + "/" + year);
//                            }
//                        }, year,
//                        month,
//                        dayOfMonth);
//                datePickerDialog.show();
//            }
//        });

        PhotoAdapter photoAdapter = new PhotoAdapter(this, getListPhoto());
        mViewPager2.setAdapter(photoAdapter);
        mCircleIndicator3.setViewPager(mViewPager2);
        ConstraintLayout room_item = findViewById(R.id.detailhotel_contrain_roomitem);
        room_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotelDetailActivity.this, RoomDetailActivity.class);
                intent.putExtra("timestart",lTimeStart);
                Log.e("thn", String.valueOf(lTimeStart));
                intent.putExtra("timestop", lTimeStop);
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
    private void updateLabel(){
        String myFormat="HH:mm:ss dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);

        timeDateStart = myCalendar.getTime();
        timestampStart = new java.sql.Timestamp(timeDateStart.getTime());
        lTimeStart = timestampStart.getTime()/1000;

        selectDateOrTimeStart.setText(dateFormat.format(myCalendar.getTime()));
        myCalendar.add(Calendar.DAY_OF_YEAR, 1);
        selectDateOrTimeStop.setText(dateFormat.format(myCalendar.getTime()));

        timeDateStop = myCalendar.getTime();
        timestampStop = new java.sql.Timestamp(timeDateStop.getTime());
        lTimeStop = timestampStop.getTime()/1000;


    }

    private void updateLabel1(){
        String myFormat="HH:mm:ss dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);

        timeDateStart = myCalendar.getTime();
        timestampStart = new java.sql.Timestamp(timeDateStart.getTime());
        lTimeStart = timestampStart.getTime()/1000;

        selectDateOrTimeStart.setText(dateFormat.format(myCalendar.getTime()));
        myCalendar.add(Calendar.DAY_OF_YEAR, 1);
        myCalendar.set(Calendar.MINUTE,00);
        myCalendar.set(Calendar.HOUR_OF_DAY,8);
        myCalendar.set(Calendar.SECOND,00);
        selectDateOrTimeStop.setText(dateFormat.format(myCalendar.getTime()));

        timeDateStop = myCalendar.getTime();
        timestampStop = new java.sql.Timestamp(timeDateStop.getTime());
        lTimeStop = timestampStop.getTime()/1000;


    }

    private void updateLabel3(){
        String myFormat="HH:mm:ss dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);

        timeDateStart = myCalendar.getTime();
        timestampStart = new java.sql.Timestamp(timeDateStart.getTime());
        lTimeStart = timestampStart.getTime()/1000;

        selectDateOrTimeStart.setText(dateFormat.format(myCalendar.getTime()));
//        myCalendar.add(Calendar.DAY_OF_YEAR, 1);
        myCalendar.set(Calendar.MINUTE,00);
        myCalendar.set(Calendar.HOUR_OF_DAY,myCalendar.get(Calendar.HOUR_OF_DAY)+1);
        myCalendar.set(Calendar.SECOND,00);
        selectDateOrTimeStop.setText(dateFormat.format(myCalendar.getTime()));

        timeDateStop = myCalendar.getTime();
        timestampStop = new java.sql.Timestamp(timeDateStop.getTime());
        lTimeStop = timestampStop.getTime()/1000;


    }
    private void updateLabel3_1(){
        String myFormat="HH:mm:ss dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.MINUTE,minuStart);
        Log.e("ert", String.valueOf(minuStart));
        myCalendar.set(Calendar.HOUR_OF_DAY,hourStart);
        myCalendar.set(Calendar.SECOND,00);

        timeDateStart = myCalendar.getTime();
        timestampStart = new java.sql.Timestamp(timeDateStart.getTime());
        lTimeStart = timestampStart.getTime()/1000;

        selectDateOrTimeStart.setText(dateFormat.format(myCalendar.getTime()));
//        myCalendar.add(Calendar.DAY_OF_YEAR, 1);
        myCalendar.set(Calendar.HOUR_OF_DAY,hourStart+1);
        selectDateOrTimeStop.setText(dateFormat.format(myCalendar.getTime()));

        timeDateStop = myCalendar.getTime();
        timestampStop = new java.sql.Timestamp(timeDateStop.getTime());
        lTimeStop = timestampStop.getTime()/1000;

    }

    private void updateLabel3_2(){
        String myFormat="HH:mm:ss dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.MINUTE,minuStop);
        Log.e("ert", String.valueOf(minuStop));
        myCalendar.set(Calendar.HOUR_OF_DAY,hourStop);
        myCalendar.set(Calendar.SECOND,00);
//        selectDateOrTimeStart.setText(dateFormat.format(myCalendar.getTime()));
//        myCalendar.add(Calendar.DAY_OF_YEAR, 1);
//        myCalendar.set(Calendar.HOUR_OF_DAY,hourStart+1);
        selectDateOrTimeStop.setText(dateFormat.format(myCalendar.getTime()));

        timeDateStop = myCalendar.getTime();
        timestampStop = new java.sql.Timestamp(timeDateStop.getTime());
        lTimeStop = timestampStop.getTime()/1000;

    }

    private void updateLabel4_1(){
        String myFormat="HH:mm:ss dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.MINUTE,minuStart);
        Log.e("ert", String.valueOf(minuStart));
        myCalendar.set(Calendar.HOUR_OF_DAY,hourStart);
        myCalendar.set(Calendar.SECOND,00);
        selectDateOrTimeStart.setText(dateFormat.format(myCalendar.getTime()));
//        myCalendar.add(Calendar.DAY_OF_YEAR, 1);
//        myCalendar.set(Calendar.HOUR_OF_DAY,hourStart+1);
//        selectDateOrTimeStop.setText(dateFormat.format(myCalendar.getTime()));

        timeDateStart = myCalendar.getTime();
        timestampStart = new java.sql.Timestamp(timeDateStart.getTime());
        lTimeStart = timestampStart.getTime()/1000;

    }
}