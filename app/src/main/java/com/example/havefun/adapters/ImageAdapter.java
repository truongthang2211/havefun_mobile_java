package com.example.havefun.adapters;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.havefun.R;


public class ImageAdapter extends PagerAdapter {
    Context context;

    public ImageAdapter(Context context){
        this.context = context;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    private int[] sliderImageId = new int[]{
            R.drawable.image1, R.drawable.image2, R.drawable.image3,R.drawable.image4, R.drawable.image5,
    };

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(sliderImageId[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return sliderImageId.length;
    }
}
