package com.example.havefun.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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

import com.example.havefun.R;
import com.example.havefun.adapters.SliderImageAdapter;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Promotion;
import com.example.havefun.models.Rating;
import com.example.havefun.models.Room;
import com.example.havefun.models.Timestamp;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.relex.circleindicator.CircleIndicator3;

public class HotelDetailActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;


    private TextView tvName,tvRoomName, tvRoomType, tvStart, tvStartComment, tvComment, tvOvernightPrice, tvDescription, tvLocation,tvitem1,tvitem2,tvitem3,tvitem4,tvitem5,tvitem6,tvitem7,txt_SLDanhgia;
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
    private Button btnTheoGio, btnQuaDem, btnTheoNgay, btnNhanPhong, btnTraPhong;
    private TextView selectDateOrTimeStart, selectDateOrTimeStop;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarStop= Calendar.getInstance();
    private Hotel currentHotel;
    private String currentOrderType;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        detailhotel_listroom_linear = findViewById(R.id.detailhotel_listroom_linear);
        detailhotel_listcmt_linear = findViewById(R.id.detailhotel_listcmt_linear);
        mViewPager2 = findViewById(R.id.view_pager_2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator_3);
        context = this;
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



        ButtonLogic();
        FetchData("hour");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ButtonLogic(){

        TimePickerDialog.OnTimeSetListener Time_onHourListener_Start = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < myCalendarStart.get(Calendar.HOUR_OF_DAY)){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Thời gian nhận phòng phải lớn hơn thời gian nhận phòng!")
                            .show();
                    return;
                }

                myCalendarStart.set(Calendar.MINUTE,minute);
                myCalendarStart.set(Calendar.HOUR_OF_DAY,hourOfDay);
                myCalendarStart.set(Calendar.SECOND,00);

                myCalendarStop.set(Calendar.MINUTE,minute);
                myCalendarStop.set(Calendar.HOUR_OF_DAY,hourOfDay+2);
                myCalendarStop.set(Calendar.SECOND,00);
                updateLabel();
            }
        };
        TimePickerDialog.OnTimeSetListener Time_onHourListener_Stop = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < myCalendarStart.get(Calendar.HOUR_OF_DAY) + 1){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Thời gian trả phòng phải ít nhất 1 tiếng so với thời gian nhận phòng!")
                            .show();
                    return;
                }

                myCalendarStop.set(Calendar.MINUTE,minute);
                myCalendarStop.set(Calendar.HOUR_OF_DAY,hourOfDay);
                myCalendarStop.set(Calendar.SECOND,00);
                updateLabel();
            }
        };

        DatePickerDialog.OnDateSetListener Date_onHourListener_Start =new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                boolean check1 = day < myCalendarStart.get(Calendar.DAY_OF_MONTH);
                boolean check2 = month < myCalendarStart.get(Calendar.MONTH);
                boolean check3 = year < myCalendarStart.get(Calendar.YEAR);
                if (check1 || check2 || check3){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Thời gian trả phòng phải ít nhất 1 tiếng so với thời gian nhận phòng!")
                            .show();
                    return;
                }

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH,month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH,day);
                myCalendarStop.set(Calendar.YEAR, year);
                myCalendarStop.set(Calendar.MONTH,month);
                myCalendarStop.set(Calendar.DAY_OF_MONTH,day);
                new TimePickerDialog(HotelDetailActivity.this, Time_onHourListener_Start, myCalendarStop.get(Calendar.HOUR_OF_DAY), myCalendarStop.get(Calendar.MINUTE), true).show();

            }
        };

        btnTheoGio.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                btnNhanPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(HotelDetailActivity.this,Date_onHourListener_Start, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                btnTraPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new TimePickerDialog(HotelDetailActivity.this, Time_onHourListener_Stop, myCalendarStop.get(Calendar.HOUR_OF_DAY), myCalendarStop.get(Calendar.MINUTE), true).show();

                    }
                });
                ResetCalendar();
                myCalendarStop.set(Calendar.HOUR_OF_DAY,myCalendarStart.get(Calendar.HOUR_OF_DAY)+2);
                updateLabel();
                FetchData("hour");
                currentOrderType = "hour";

            }


        });
        DatePickerDialog.OnDateSetListener Daily_Listener_Start =new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                boolean check1 = day < myCalendarStart.get(Calendar.DAY_OF_MONTH);
                boolean check2 = month < myCalendarStart.get(Calendar.MONTH);
                boolean check3 = year < myCalendarStart.get(Calendar.YEAR);
                if (check1 || check2 || check3){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Thời gian trả phòng phải ít nhất 1 tiếng so với thời gian nhận phòng!")
                            .show();
                    return;
                }
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH,month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH,day);
                myCalendarStart.set(Calendar.MINUTE,00);
                myCalendarStart.set(Calendar.HOUR_OF_DAY,12);
                myCalendarStart.set(Calendar.SECOND,00);

                myCalendarStop.set(Calendar.YEAR, year);
                myCalendarStop.set(Calendar.MONTH,month);
                myCalendarStop.set(Calendar.DAY_OF_MONTH,day+1);
                myCalendarStop.set(Calendar.MINUTE,00);
                myCalendarStop.set(Calendar.HOUR_OF_DAY,14);
                myCalendarStop.set(Calendar.SECOND,00);
                updateLabel();
            }
        };

        DatePickerDialog.OnDateSetListener Daily_Listener_Stop =new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                boolean check1 = day <= myCalendarStart.get(Calendar.DAY_OF_MONTH);
                boolean check2 = month < myCalendarStart.get(Calendar.MONTH);
                boolean check3 = year < myCalendarStart.get(Calendar.YEAR);
                if (check1 || check2 || check3){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Thời gian trả phòng phải ít nhất 1 ngày so với thời gian nhận phòng!")
                            .show();
                    return;
                }

                myCalendarStop.set(Calendar.YEAR, year);
                myCalendarStop.set(Calendar.MONTH,month);
                myCalendarStop.set(Calendar.DAY_OF_MONTH,day);
                myCalendarStop.set(Calendar.MINUTE,00);
                myCalendarStop.set(Calendar.HOUR_OF_DAY,14);
                myCalendarStop.set(Calendar.SECOND,00);
                updateLabel();
            }
        };

        DatePickerDialog.OnDateSetListener Overnight_Listener_Start =new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                boolean check1 = day < myCalendarStart.get(Calendar.DAY_OF_MONTH);
                boolean check2 = month < myCalendarStart.get(Calendar.MONTH);
                boolean check3 = year < myCalendarStart.get(Calendar.YEAR);
                if (check1 || check2 || check3){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Thời gian trả phòng phải ít nhất 1 tiếng so với thời gian nhận phòng!")
                            .show();
                    return;
                }
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH,month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH,day);
                myCalendarStart.set(Calendar.MINUTE,00);
                myCalendarStart.set(Calendar.HOUR_OF_DAY,21);
                myCalendarStart.set(Calendar.SECOND,00);
                myCalendarStop.set(Calendar.YEAR, year);
                myCalendarStop.set(Calendar.MONTH,month);
                myCalendarStop.set(Calendar.DAY_OF_MONTH,day+1);
                myCalendarStop.set(Calendar.MINUTE,00);
                myCalendarStop.set(Calendar.HOUR_OF_DAY,11);
                myCalendarStop.set(Calendar.SECOND,00);

                updateLabel();

            }
        };



        btnTheoNgay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                btnNhanPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(HotelDetailActivity.this,Daily_Listener_Start, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                btnTraPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(HotelDetailActivity.this,Daily_Listener_Stop, myCalendarStop.get(Calendar.YEAR), myCalendarStop.get(Calendar.MONTH), myCalendarStop.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                ResetCalendar();
                LocalDateTime now = LocalDateTime.now();

                myCalendarStart.set(Calendar.YEAR, now.getYear());
                myCalendarStart.set(Calendar.MONTH,now.getMonthValue()-1);
                myCalendarStart.set(Calendar.DAY_OF_MONTH,now.getDayOfMonth());
                myCalendarStart.set(Calendar.MINUTE,00);
                myCalendarStart.set(Calendar.HOUR_OF_DAY,12);
                myCalendarStart.set(Calendar.SECOND,00);

                myCalendarStop.set(Calendar.YEAR, now.getYear());
                myCalendarStop.set(Calendar.MONTH,now.getMonthValue()-1);
                myCalendarStop.set(Calendar.DAY_OF_MONTH,now.getDayOfMonth()+1);
                myCalendarStop.set(Calendar.MINUTE,00);
                myCalendarStop.set(Calendar.HOUR_OF_DAY,14);
                myCalendarStop.set(Calendar.SECOND,00);
                updateLabel();
                FetchData("daily");
                currentOrderType = "daily";

            }
        });


        btnQuaDem.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                btnNhanPhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(HotelDetailActivity.this,Overnight_Listener_Start, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                btnTraPhong.setOnClickListener(null);
                ResetCalendar();
                LocalDateTime now = LocalDateTime.now();
                myCalendarStart.set(Calendar.HOUR_OF_DAY,21);
                myCalendarStart.set(Calendar.MINUTE,0);
                myCalendarStop.set(Calendar.MINUTE,0);
                myCalendarStop.set(Calendar.HOUR_OF_DAY,11);
                updateLabel();
                FetchData("overnight");
                currentOrderType = "overnight";
            }
        });
        currentOrderType = "hour";
        btnNhanPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(HotelDetailActivity.this,Date_onHourListener_Start, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnTraPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(HotelDetailActivity.this, Time_onHourListener_Stop, myCalendarStop.get(Calendar.HOUR_OF_DAY), myCalendarStop.get(Calendar.MINUTE), true).show();

            }
        });
        ResetCalendar();
        myCalendarStop.set(Calendar.HOUR_OF_DAY,myCalendarStart.get(Calendar.HOUR_OF_DAY)+2);
        updateLabel();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ResetCalendar(){
        LocalDateTime now = LocalDateTime.now();
        myCalendarStart.set(Calendar.YEAR, now.getYear());
        myCalendarStart.set(Calendar.MONTH,now.getMonthValue()-1);
        myCalendarStart.set(Calendar.DAY_OF_MONTH,now.getDayOfMonth());
        myCalendarStart.set(Calendar.MINUTE,now.getMinute());
        myCalendarStart.set(Calendar.HOUR_OF_DAY,now.getHour());
        myCalendarStart.set(Calendar.SECOND,0);

        myCalendarStop.set(Calendar.YEAR, now.getYear());
        myCalendarStop.set(Calendar.MONTH,now.getMonthValue()-1);
        myCalendarStop.set(Calendar.DAY_OF_MONTH,now.getDayOfMonth());
        myCalendarStop.set(Calendar.MINUTE,now.getMinute());
        myCalendarStop.set(Calendar.HOUR_OF_DAY,now.getHour());
        myCalendarStop.set(Calendar.SECOND,00);
    }
    private void ClearButtonBackground(){
        btnQuaDem.setBackgroundTintList(context.getResources().getColorStateList(R.color.darkWhite));
        btnQuaDem.setTextColor(context.getResources().getColorStateList(R.color.lightBlack));

        btnTheoNgay.setBackgroundTintList(context.getResources().getColorStateList(R.color.darkWhite));
        btnTheoNgay.setTextColor(context.getResources().getColorStateList(R.color.lightBlack));

        btnTheoGio.setBackgroundTintList(context.getResources().getColorStateList(R.color.darkWhite));
        btnTheoGio.setTextColor(context.getResources().getColorStateList(R.color.lightBlack));
    }
    private void FetchData(String option){
        Intent intent = getIntent();
        String hotelStr = intent.getStringExtra("hotel");
        Hotel h = new Gson().fromJson(hotelStr,Hotel.class);
        currentHotel = h;
        tvName.setText(h.getName());
        tvLocation.setText(h.getLocation().getAddress() + ", " +  h.getLocation().getDistrict()+ ", " + h.getLocation().getCity());
        SliderImageAdapter adapter = new SliderImageAdapter(new ArrayList<>(Arrays.asList(h.getImgs())),this);
        mViewPager2.setAdapter(adapter);
        tvDescription.setText(h.getDescription());
        mCircleIndicator3.setViewPager(mViewPager2);
        if (h.getRatings()!=null){
            tvStartComment.setText(String.valueOf(h.getRatings().length));
            txt_SLDanhgia.setText("("+ h.getRatings().length + " đánh giá)");

        }

        LayoutInflater inflater = LayoutInflater.from(context);

        detailhotel_listroom_linear.removeAllViews();
        for (Room r : h.getRooms()){
            View view = inflater.inflate(R.layout.room_card_item,detailhotel_listroom_linear,false);
            Promotion p = null;
            if (h.getPromotions() != null && h.getPromotions().length>0){
                p = h.getPromotions()[0];
            }
            RenderRooms(view,r,p,option );
            detailhotel_listroom_linear.addView(view);

        }
        detailhotel_listcmt_linear.removeAllViews();
        if (h.getRatings()!=null){
            for (Rating ra : h.getRatings()){
                View view  = inflater.inflate(R.layout.comment_item_card,detailhotel_listcmt_linear,false);
                RenderComment(view,ra);
                detailhotel_listcmt_linear.addView(view);
            }
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
        view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                long second_start = LocalDateTime.ofInstant(myCalendarStart.toInstant(), myCalendarStart.getTimeZone().toZoneId()).atZone(ZoneId.systemDefault()).toEpochSecond();
                long second_stop = LocalDateTime.ofInstant(myCalendarStop.toInstant(), myCalendarStop.getTimeZone().toZoneId()).atZone(ZoneId.systemDefault()).toEpochSecond();
                Timestamp timestart = new Timestamp(second_start,0);
                Timestamp timestop = new Timestamp(second_stop,0);

                Intent intent = new Intent(context,RoomDetailActivity.class);
                intent.putExtra("time_start",new Gson().toJson(timestart));
                intent.putExtra("time_stop",new Gson().toJson(timestop));
                intent.putExtra("room",new Gson().toJson(r));
                intent.putExtra("hotel_id",currentHotel.getId());
                intent.putExtra("order_type",currentOrderType);
                intent.putExtra("price",price.getText());
                startActivity(intent);
            }
        });
    }
    private void RenderComment(View view, Rating rating){
        TextView user = view.findViewById(R.id.TT_BinhLuan);
        TextView content = view.findViewById(R.id.nd_BinhLuan);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setRating(rating.getStart());
        user.setText(rating.getUser().getPhone().substring(0,6)+"****");
        content.setText(rating.getComment());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateLabel(){
        long second_start = LocalDateTime.ofInstant(myCalendarStart.toInstant(), myCalendarStart.getTimeZone().toZoneId()).atZone(ZoneId.systemDefault()).toEpochSecond();
        long second_stop = LocalDateTime.ofInstant(myCalendarStop.toInstant(), myCalendarStop.getTimeZone().toZoneId()).atZone(ZoneId.systemDefault()).toEpochSecond();
        Timestamp timestart = new Timestamp(second_start,0);
        Timestamp timestop = new Timestamp(second_stop,0);

        selectDateOrTimeStart.setText(timestart.toString());
        selectDateOrTimeStop.setText(timestop.toString());



    }


}