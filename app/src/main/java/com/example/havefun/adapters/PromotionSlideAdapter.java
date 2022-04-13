package com.example.havefun.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havefun.R;

public class PromotionSlideAdapter extends RecyclerView.Adapter<PromotionSlideAdapter.ViewHolder> {
    int[] promotion_imgs;

    public PromotionSlideAdapter(int[] promotion_imgs) {
        this.promotion_imgs = promotion_imgs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_promotion_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.promotion_imv.setBackgroundResource(promotion_imgs[position]);
    }

    @Override
    public int getItemCount() {
        return promotion_imgs.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView promotion_imv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            promotion_imv = itemView.findViewById(R.id.home_im_promotion);

        }
    }
}
