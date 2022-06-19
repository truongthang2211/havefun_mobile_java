package com.example.havefun.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havefun.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderImageAdapter extends RecyclerView.Adapter<SliderImageAdapter.ViewHolder> {
    ArrayList<Uri> imgUris=null;
    ArrayList<String> imgsStr=null;
    Context context;

    public SliderImageAdapter(Context context, ArrayList<Uri> imgUris) {
        this.imgUris = imgUris;
        this.context = context;
    }

    public SliderImageAdapter(ArrayList<String> imgsStr,Context context ) {
        this.imgsStr = imgsStr;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_imgs_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (imgUris==null){
            Picasso.get().load(imgsStr.get(position)).into(holder.slide_img);
        }else {
            Uri uri = imgUris.get(position);
            holder.slide_img.setImageURI(uri);
        }


    }

    @Override
    public int getItemCount() {
        if (imgUris == null && imgsStr==null)
            return 0;
        if (imgsStr!=null)
            return imgsStr.size();
        return imgUris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView slide_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slide_img = itemView.findViewById(R.id.slider_img);

        }
    }
}
