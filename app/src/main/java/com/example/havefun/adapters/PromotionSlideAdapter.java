package com.example.havefun.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havefun.R;
import com.example.havefun.activities.DetailPromotionActivity;
import com.example.havefun.activities.SearchActivity;
import com.example.havefun.models.Promotion;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PromotionSlideAdapter extends RecyclerView.Adapter<PromotionSlideAdapter.ViewHolder> {
    ArrayList<Promotion> promotions;
    Context context;
    public PromotionSlideAdapter(Context context, ArrayList<Promotion> promotions) {
        this.promotions = promotions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_promotion_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.promotion_imv.setBackgroundResource(promotion_imgs[position]);
        Promotion thispro = promotions.get(position);
        Picasso.get().load(thispro.getImg()).into( holder.promotion_imv);
        holder.promotion_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailPromotionActivity.class);
                intent.putExtra("promotion",new Gson().toJson(thispro));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView promotion_imv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            promotion_imv = itemView.findViewById(R.id.home_im_promotion);

        }
    }
}
