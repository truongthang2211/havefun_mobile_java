package com.example.havefun.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havefun.R;
import com.example.havefun.models.Hotel;

import java.util.ArrayList;

public class HotDealSildeAdapter extends RecyclerView.Adapter<HotDealSildeAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Hotel> modelArrayList;

    public HotDealSildeAdapter(Context context, ArrayList<Hotel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_large_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TvHotelName.setText(modelArrayList.get(position).getName());
        holder.TvPromotion.setText(modelArrayList.get(position).getPromotion());
        holder.TvTimePromotion.setText(modelArrayList.get(position).getTimePromotion());
        holder.TvPrice.setText(String.valueOf(modelArrayList.get(position).getPrice()));
        holder.TvPriceDiscount.setText(String.valueOf(modelArrayList.get(position).getDiscount()));
        holder.TvLocation.setText(modelArrayList.get(position).getLocation());
        holder.TvRate.setText(String.valueOf(modelArrayList.get(position).getRate()));
        holder.TvNumRate.setText(String.valueOf(modelArrayList.get(position).getNum_rate()));
        holder.IvImg.setImageResource(modelArrayList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
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
