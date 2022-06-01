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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeHotHotelAdapter extends RecyclerView.Adapter<HomeHotHotelAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Hotel> modelArrayList;

    public HomeHotHotelAdapter(Context context, ArrayList<Hotel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_small_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TvHotelName.setText(modelArrayList.get(position).getName());
        holder.TvRate.setText(String.valueOf(modelArrayList.get(position).getAvgStar()));
        holder.TvNumRate.setText(String.valueOf(modelArrayList.get(position).getRatings().length));
        Picasso.get().load(modelArrayList.get(position).getImgs()[0]).into( holder.IvImg);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView TvHotelName;
        private TextView TvRate;
        private TextView TvNumRate;
        private ImageView IvImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TvHotelName = (TextView) itemView.findViewById(R.id.home_smallcard_name);
            TvRate = (TextView) itemView.findViewById(R.id.home_smallcard_rate);
            TvNumRate = (TextView) itemView.findViewById(R.id.home_smallcard_numrate);
            IvImg = (ImageView) itemView.findViewById(R.id.home_smallcard_img);

        }
    }
}
