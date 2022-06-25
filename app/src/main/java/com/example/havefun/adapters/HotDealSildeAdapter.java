package com.example.havefun.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havefun.R;
import com.example.havefun.activities.HotelDetailActivity;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Promotion;
import com.example.havefun.models.Room;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HotDealSildeAdapter extends RecyclerView.Adapter<HotDealSildeAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Hotel> modelArrayList;
    private boolean overnight_type=false;

    public HotDealSildeAdapter(Context context, ArrayList<Hotel> modelArrayList,boolean overnight_type) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.overnight_type = overnight_type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_large_card,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Promotion promotion=new Promotion();
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("en", "EN"));
        Room first_room = modelArrayList.get(position).getRooms()[0];
        holder.TvPromotion.setVisibility(View.INVISIBLE);
        holder.TvHotelName.setText(modelArrayList.get(position).getName());
        holder.TvLocation.setText(modelArrayList.get(position).getLocation().getDistrict());
        holder.TvPrice.setText(currencyFormatter.format(first_room.getHour_price())+" đ");
        holder.TvTimePromotion.setText("Theo giờ");
        if (modelArrayList.get(position).getRatings()!=null&& modelArrayList.get(position).getRatings().length > 0){
            holder.TvRate.setText(String.valueOf(modelArrayList.get(position).getAvgStar()));
            holder.TvNumRate.setText(String.valueOf(modelArrayList.get(position).getRatings().length));

        }
        if (modelArrayList.get(position).getPromotions().length>0){
            holder.TvPromotion.setVisibility(View.VISIBLE);
            promotion = modelArrayList.get(position).getPromotions()[0];
            holder.TvPromotion.setText(String.valueOf("-"+currencyFormatter.format(promotion.getDiscount_ratio()*100)+ "%"));
            if (overnight_type){

                float price =first_room.getOvernight_price();
                holder.TvPrice.setText(currencyFormatter.format(price*(1-promotion.getDiscount_ratio()))+" đ");
                holder.TvPriceDiscount.setText(currencyFormatter.format(price)+" đ");
                holder.TvPriceDiscount.setPaintFlags(holder.TvPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.TvTimePromotion.setText("Qua đêm");

            }else{
                String type_pro = promotion.getOrder_type()[0] == "overnight"?"Qua đêm": promotion.getOrder_type()[0] == "daily"?"Theo ngày":"Theo giờ";
                float price = type_pro=="Qua đêm"?first_room.getOvernight_price():type_pro=="Theo ngày"?first_room.getDaily_price():first_room.getHour_price();
                holder.TvPrice.setText(currencyFormatter.format(price*(1-promotion.getDiscount_ratio()))+" đ");
                holder.TvPriceDiscount.setText(currencyFormatter.format(price)+" đ");
                holder.TvPriceDiscount.setPaintFlags(holder.TvPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.TvTimePromotion.setText(type_pro);

            }
        }
        Picasso.get().load(modelArrayList.get(position).getImgs()[0]).into( holder.IvImg);

        Hotel currentHotel = modelArrayList.get(position);
        holder.CVContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HotelDetailActivity.class);
                intent.putExtra("hotel",new Gson().toJson(currentHotel));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView CVContainer;
        private TextView TvHotelName;
        private TextView TvPromotion;
        private TextView TvTimePromotion;
        private TextView TvPrice;
        private TextView TvPriceDiscount;
        private TextView TvLocation;
        private TextView TvRate;
        private TextView TvNumRate;
        private ImageView IvImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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

        }
    }
}
