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
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.example.havefun.adapters.ImageAdapter;
import com.example.havefun.adapters.PhotoAdapter;
import com.example.havefun.adapters.SliderImageAdapter;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Location;
import com.example.havefun.models.Photo;
import com.example.havefun.models.Promotion;
import com.example.havefun.models.Rating;
import com.example.havefun.models.Room;
import com.example.havefun.utils.MySingleton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Array;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import me.relex.circleindicator.CircleIndicator3;

public class HotelDetailActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;


    private TextView tvName,tvRoomName, tvRoomType, tvStart, tvStartComment, tvComment, tvOvernightPrice, tvDescription, tvLocation,tvitem1,tvitem2,tvitem3,tvitem4,tvitem5,tvitem6,tvitem7,txt_SLDanhgia;
    private ImageView img1, img2, img3, img4, img5, img6,img7;
    private boolean wood_floor;
    private boolean air_conditioning;
    private boolean reception24;
    private boolean wifi;
    private boolean cable_tv;
    private boolean elevator;
    private boolean tv;
    private Context context;
    private LinearLayout detailhotel_listroom_linear;
    private LinearLayout detailhotel_listcmt_linear;

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
        detailhotel_listroom_linear = findViewById(R.id.detailhotel_listroom_linear);
        detailhotel_listcmt_linear = findViewById(R.id.detailhotel_listcmt_linear);
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
        txt_SLDanhgia = findViewById(R.id.txt_SLDanhgia);



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
        FetchData("hour");
    }
    private void FetchData(String option){
        Intent intent = getIntent();
        String hotelStr = intent.getStringExtra("hotel");
        Hotel h = new Gson().fromJson(hotelStr,Hotel.class);
        txt_SLDanhgia.setText("("+ h.getRatings().length + " đánh giá)");
        tvName.setText(h.getName());
        tvLocation.setText(h.getLocation().getAddress() + ", " +  h.getLocation().getDistrict()+ ", " + h.getLocation().getCity());
        SliderImageAdapter adapter = new SliderImageAdapter(new ArrayList<>(Arrays.asList(h.getImgs())),this);
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);

        tvStartComment.setText(h.getRatings().length);

        LayoutInflater inflater = LayoutInflater.from(context);

        detailhotel_listroom_linear.removeAllViews();
        for (Room r : h.getRooms()){
            View view = inflater.inflate(R.layout.room_card_item,detailhotel_listroom_linear,false);

            RenderRooms(view,r,h.getPromotions()[0],option );
            detailhotel_listroom_linear.addView(view);

        }
        for (Rating ra : h.getRatings()){
            View view  = inflater.inflate(R.layout.comment_item_card,detailhotel_listcmt_linear,false);
            RenderComment(view,ra);
            detailhotel_listcmt_linear.addView(view);
        }
    }
    private void RenderRooms(View view, Room r, Promotion pro,String option){
        ImageView img = (ImageView)view.findViewById(R.id.imageView);
        Picasso.get().load(r.getImgs()[0]).into(img);
        TextView name = (TextView)view.findViewById(R.id.textView6);
        name.setText(r.getName());
        String condition = "";
        if (r.getRoom_conditions().isDouble_bed()){
            condition += "Giường đôi ";
        }
        if (r.getRoom_conditions().isArea_20m2()){
            condition += "20m2 ";
        }
        if (r.getRoom_conditions().isWindow()){
            condition += "Cửa sổ";
        }
        TextView conditionTv = view.findViewById(R.id.textView7);
        conditionTv.setText(condition);
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("en", "EN"));
        TextView price = view.findViewById(R.id.textView10);
        TextView price_pro = view.findViewById(R.id.tv_promotion_price_room);
        TextView ordertype = view.findViewById(R.id.textView9);
        price_pro.setPaintFlags(price_pro.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if (option.equals("hour")){
            price.setText(currencyFormatter.format(r.getHour_price()) + " đ");
            ordertype.setText("Theo giờ");
        }else if (option.equals("daily")){
            price.setText(currencyFormatter.format(r.getDaily_price()) + " đ");
            ordertype.setText("Theo ngày");
        }else{
            price.setText(currencyFormatter.format(r.getOvernight_price()) + " đ");
            ordertype.setText("Qua đêm");
        }
        if (pro== null){
            TextView uudai = view.findViewById(R.id.textView2);
            TextView giamngay = view.findViewById(R.id.textView4);
            uudai.setVisibility(View.INVISIBLE);
            giamngay.setVisibility(View.INVISIBLE);

        }else{
            ArrayList<String> orderstype = new ArrayList<String>(Arrays.asList(pro.getOrder_type()));
            if (orderstype.contains(option)){
                if (option.equals("hour")){
                    price.setText(currencyFormatter.format(r.getHour_price()*(1-pro.getDiscount_ratio())) + " đ");
                }else if (option.equals("daily")){
                    price.setText(currencyFormatter.format(r.getDaily_price()*(1-pro.getDiscount_ratio())) + " đ");
                }else{
                    price.setText(currencyFormatter.format(r.getOvernight_price()*(1-pro.getDiscount_ratio())) + " đ");
                }
            }
        }

    }
    private void RenderComment(View view, Rating rating){
        TextView user = view.findViewById(R.id.TT_BinhLuan);
        TextView content = view.findViewById(R.id.nd_BinhLuan);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setRating(rating.getStart());
        user.setText(rating.getUser().getPhone().substring(0,6)+"****");
        content.setText(rating.getComment());
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