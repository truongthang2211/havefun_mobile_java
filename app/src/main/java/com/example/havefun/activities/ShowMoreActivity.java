package com.example.havefun.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.havefun.R;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Promotion;
import com.example.havefun.models.Room;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ShowMoreActivity extends AppCompatActivity {
    TextView TypeTitle;
    LinearLayout ListHotelLinear;
    ImageView back;
    ImageView sort;
    List<Hotel> listHotel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);

        back = findViewById(R.id.showmore_back_ImgV);
        ListHotelLinear = findViewById(R.id.showmore_listhotel_linear);
        TypeTitle = findViewById(R.id.showmore_typelistHotel_Tv);
        sort = findViewById(R.id.showmore_sort_imgV);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortDialog();
            }
        });
        Intent intent = getIntent();
        String hotelsStr = intent.getStringExtra("listHotel");
        TypeTitle.setText(intent.getStringExtra("TypeTitle"));
        listHotel = Arrays.asList(new GsonBuilder().create().fromJson(hotelsStr, Hotel[].class));
        if (intent.getStringExtra("TypeTitle").equals("Khách sạn mới")){
            listHotel.removeIf(s-> s.getCreated_at().toLocalDateTime().plusDays(60).isBefore(LocalDateTime.now()));
        }
        ClearAndAddHotels();
    }

    private void showSortDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sort_bottom_popup);

        TextView increasePrice = dialog.findViewById(R.id.sort_increase_price_TV);
        TextView decreasePrice = dialog.findViewById(R.id.sort_decrease_price_TV);
        TextView decreaseRate = dialog.findViewById(R.id.sort_decrease_rate_TV);


        increasePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increasePrice.setBackgroundColor(Color.parseColor("#FFE5D5"));
                decreasePrice.setBackgroundColor(0);
                decreaseRate.setBackgroundColor(0);
                Collections.sort(listHotel, new Comparator<Hotel>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public int compare(Hotel a, Hotel b) {
                        float a_price = a.getRooms()[0].getHour_price();
                        float b_price = b.getRooms()[0].getHour_price();
                        if (a.getPromotions() != null && a.getPromotions().length > 0) {
                            a_price *= (1 - a.getPromotions()[0].getDiscount_ratio());
                        }
                        if (b.getPromotions() != null && b.getPromotions().length > 0) {
                            b_price *= (1 - b.getPromotions()[0].getDiscount_ratio());
                        }
                        return a_price > b_price ? -1 : a_price < b_price ? 1 : 0;
                    }
                });

                ClearAndAddHotels();
            }
        });
        decreasePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increasePrice.setBackgroundColor(0);
                decreasePrice.setBackgroundColor(Color.parseColor("#FFE5D5"));
                decreaseRate.setBackgroundColor(0);
                Collections.sort(listHotel, new Comparator<Hotel>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public int compare(Hotel a, Hotel b) {
                        float a_price = a.getRooms()[0].getHour_price();
                        float b_price = b.getRooms()[0].getHour_price();
                        if (a.getPromotions() != null && a.getPromotions().length > 0) {
                            a_price *= (1 - a.getPromotions()[0].getDiscount_ratio());
                        }
                        if (b.getPromotions() != null && b.getPromotions().length > 0) {
                            b_price *= (1 - b.getPromotions()[0].getDiscount_ratio());
                        }
                        return a_price > b_price ? 1 : a_price < b_price ? -1 : 0;
                    }
                });
                ClearAndAddHotels();

            }
        });
        decreaseRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increasePrice.setBackgroundColor(0);
                decreasePrice.setBackgroundColor(0);
                decreaseRate.setBackgroundColor(Color.parseColor("#FFE5D5"));
                Collections.sort(listHotel, new Comparator<Hotel>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public int compare(Hotel a, Hotel b) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return a.getAvgStar() > b.getAvgStar() ? -1 : a.getAvgStar() < b.getAvgStar() ? 1 : 0;
                    }
                });
                ClearAndAddHotels();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void ClearAndAddHotels() {
        ListHotelLinear.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Hotel h : listHotel) {
            View view = inflater.inflate(R.layout.home_large_card, ListHotelLinear, false);
            SetHotelToCard(view, h);
            ListHotelLinear.addView(view);
        }
    }

    private void SetHotelToCard(View itemView, Hotel hotel) {
        MaterialCardView CVContainer;
        TextView TvHotelName;
        TextView TvPromotion;
        TextView TvTimePromotion;
        TextView TvPrice;
        TextView TvPriceDiscount;
        TextView TvLocation;
        TextView TvRate;
        TextView TvNumRate;
        ImageView IvImg;
        CVContainer = (MaterialCardView) itemView.findViewById(R.id.home_large_card);
        TvHotelName = (TextView) itemView.findViewById(R.id.home_cardview_nameTv);
        TvPromotion = (TextView) itemView.findViewById(R.id.home_cardview_promotionTv);
        TvTimePromotion = (TextView) itemView.findViewById(R.id.home_cardview_promotionTimeTv);
        TvPrice = (TextView) itemView.findViewById(R.id.home_cardview_priceTv);
        TvPriceDiscount = (TextView) itemView.findViewById(R.id.home_cardview_priceDiscountTv);
        TvLocation = (TextView) itemView.findViewById(R.id.home_cardview_locationTv);
        TvRate = (TextView) itemView.findViewById(R.id.home_cardview_rateTv);
        TvNumRate = (TextView) itemView.findViewById(R.id.home_cardview_numRateTv);
        IvImg = (ImageView) itemView.findViewById(R.id.home_cardview_img);

        Promotion promotion = new Promotion();
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("en", "EN"));
        Room first_room = hotel.getRooms()[0];
        TvPromotion.setVisibility(View.INVISIBLE);
        TvHotelName.setText(hotel.getName());
        TvLocation.setText(hotel.getLocation().getDistrict());
        TvPrice.setText(currencyFormatter.format(first_room.getHour_price()) + " đ");
        TvTimePromotion.setText("Theo giờ");
        if (hotel.getRatings() != null && hotel.getRatings().length > 0) {
            TvRate.setText(String.valueOf(hotel.getAvgStar()));
            TvNumRate.setText(String.valueOf(hotel.getRatings().length));

        }
        if (hotel.getPromotions().length > 0) {
            promotion = hotel.getPromotions()[0];
            TvPromotion.setVisibility(View.VISIBLE);
            TvPromotion.setText(String.valueOf("-" + currencyFormatter.format(promotion.getDiscount_ratio() * 100) + "%"));
            String type_pro = "Theo giờ";
            float price = first_room.getHour_price();
            TvPrice.setText(currencyFormatter.format(price * promotion.getDiscount_ratio()) + " đ");
            TvPriceDiscount.setText(currencyFormatter.format(price) + " đ");
            TvPriceDiscount.setPaintFlags(TvPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            TvTimePromotion.setText(type_pro);
        }
        Picasso.get().load(hotel.getImgs()[0]).into(IvImg);


        CVContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowMoreActivity.this, HotelDetailActivity.class);
                intent.putExtra("hotel",new Gson().toJson(hotel));
                startActivity(intent);
            }
        });
    }
}