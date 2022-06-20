package com.example.havefun.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havefun.R;
import com.example.havefun.activities.HotelDetailActivity;
import com.example.havefun.models.Hotel;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
    private List<Hotel> listHotel;
    private List<Hotel> listHotelOld;
    Context context;
    public SearchAdapter(Context context,List<Hotel> listHotel) {
        this.context = context;
        this.listHotel = listHotel;
        this.listHotelOld = listHotel;

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_normal_card, parent, false);
        return new SearchViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Hotel h = listHotel.get(position);
        if (h == null)
            return;
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("en", "EN"));
        holder.name.setText(h.getName());
        holder.price.setText(currencyFormatter.format(h.getRooms()[0].getHour_price()) + " đ");
        holder.location.setText(h.getLocation().getDistrict() + " - " +h.getLocation().getCity());
        if (h.getRatings() != null) {
            holder.rate.setText(currencyFormatter.format(h.getAvgStar()));
            holder.numrate.setText(String.valueOf(h.getRatings().length));
        }
        Picasso.get().load(h.getImgs()[0]).into(holder.img);
        if (h.getPromotions().length > 0) {
            holder.promotion.setVisibility(View.VISIBLE);
            holder.promotion.setText(String.valueOf("-" + currencyFormatter.format(h.getPromotions()[0].getDiscount_ratio() * 100)) + "%");

            holder.price.setText(currencyFormatter.format(h.getRooms()[0].getHour_price()*(1-h.getPromotions()[0].getDiscount_ratio())) + " đ");

            holder.discount_price.setText(currencyFormatter.format(h.getRooms()[0].getHour_price()) + " đ");
            holder.discount_price.setPaintFlags(holder.discount_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (h.getCreated_at().toLocalDateTime().plusDays(60).isAfter(LocalDateTime.now())) {
            holder.newHotel.setVisibility(View.VISIBLE);
        }
        holder.home_norcard_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HotelDetailActivity.class);
                intent.putExtra("hotel",new Gson().toJson(h));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listHotel != null) {
            return listHotel.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    listHotel = listHotelOld;
                }else{
                    List<Hotel> list = new ArrayList<>();
                    for (Hotel h : listHotelOld ){
                        boolean name =h.getName().toLowerCase().contains(strSearch.toLowerCase());
                        boolean city = h.getLocation().getCity().toLowerCase().contains(strSearch.toLowerCase());
                        boolean district = h.getLocation().getDistrict().toLowerCase().contains(strSearch.toLowerCase());
                        boolean address = h.getLocation().getAddress().toLowerCase().contains(strSearch.toLowerCase());
                        if (name || city || district || address){
                            list.add(h);
                        }
                    }
                    listHotel = list;
                }
                FilterResults  rs= new FilterResults();
                rs.values= listHotel;
                return rs;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listHotel = (List<Hotel>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView newHotel;
        private TextView name;
        private TextView price;
        private TextView discount_price;
        private TextView promotion;
        private TextView rate;
        private TextView numrate;
        private TextView location;
        private ImageView img;
        private MaterialCardView home_norcard_cardview;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            newHotel = itemView.findViewById(R.id.home_norcard_tv_new);
            name = itemView.findViewById(R.id.home_norcard_name);
            price = itemView.findViewById(R.id.home_norcard_price);
            promotion = itemView.findViewById(R.id.home_norcard_promotion);
            rate = itemView.findViewById(R.id.home_norcard_rate);
            numrate = itemView.findViewById(R.id.home_normal_numrate);
            discount_price = itemView.findViewById(R.id.home_price_discounted_Tv);
            img = itemView.findViewById(R.id.home_norcard_img);
            location = itemView.findViewById(R.id.home_norcard_location);
            home_norcard_cardview = itemView.findViewById(R.id.home_norcard_cardview);
        }
    }
}
