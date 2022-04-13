package com.example.havefun.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.havefun.R;
import com.example.havefun.adapters.HomeHotHotelAdapter;
import com.example.havefun.adapters.HotDealSildeAdapter;
import com.example.havefun.adapters.PromotionSlideAdapter;
import com.example.havefun.databinding.FragmentHomeBinding;
import com.example.havefun.models.Hotel;
import com.example.havefun.viewmodels.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ViewPager2 Promotion_viewpager;
    private ViewPager2 HotDeal_viewpager;
    private ViewPager2 Special_viewpager;
    private ArrayList<Hotel> modelArrayList;
    private HotDealSildeAdapter hotDealAdapter;
    private HotDealSildeAdapter specialAdapter;
    private PromotionSlideAdapter promotionAdapter;
    private HomeHotHotelAdapter hotHotelAdapter;
    private LinearLayout linearHotHotel;
    private LinearLayout linearTopHotel;
    private LinearLayout linearMoreHotel;
    private FragmentHomeBinding binding;
    private Context context;
    int[] promotion_imgs = {R.drawable.home_promotion1,R.drawable.home_promotion2,R.drawable.home_promotion3};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.context = container.getContext();
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HotDeal_viewpager = (ViewPager2) getView().findViewById(R.id.home_viewpager_hotdeal);
        Promotion_viewpager = (ViewPager2) getView().findViewById(R.id.home_viewpager_promotion);
        Special_viewpager = (ViewPager2) getView().findViewById(R.id.home_viewpager_specialpromo);

        linearHotHotel=getView().findViewById(R.id.home_linear_hothotel);
        linearTopHotel=getView().findViewById(R.id.home_linear_toprate);
        linearMoreHotel=getView().findViewById(R.id.home_linear_morehotel);


        loadCard();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadCard() {
        modelArrayList = new ArrayList<>();
        Hotel test1 = new Hotel("Khách sạn ma ám 1","Nghĩa trang","Giảm ngay","Qua đêm",69000,50000,1,1,R.drawable.home_hotel1);
        Hotel test2 = new Hotel("Khách sạn ma ám 2","Nghĩa trang","Giảm ngay","Qua đêm",69000,50000,1,1,R.drawable.home_hotel2);
        Hotel test3 = new Hotel("Khách sạn ma ám 3","Nghĩa trang","Giảm ngay","Qua đêm",69000,50000,1,1,R.drawable.home_hotel3);
        modelArrayList.add(test1);
        modelArrayList.add(test2);
        modelArrayList.add(test3);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        for (Hotel item : modelArrayList) {
            View view  = inflater.inflate(R.layout.home_small_card, linearHotHotel, false);
            View view2  = inflater.inflate(R.layout.home_small_card, linearTopHotel, false);
            View view3  = inflater.inflate(R.layout.home_normal_card, linearTopHotel, false);
            // set item content in view
            SetSmallCard(view,item);
            SetSmallCard(view2,item);
            SetNormalCard(view3,item);
            linearHotHotel.addView(view);
            linearTopHotel.addView(view2);
            linearMoreHotel.addView(view3);
        }

        hotDealAdapter = new HotDealSildeAdapter(this.context,modelArrayList);
        HotDeal_viewpager.setAdapter(hotDealAdapter);

        specialAdapter=new HotDealSildeAdapter(this.context,modelArrayList);
        Special_viewpager.setAdapter(specialAdapter);

        promotionAdapter = new PromotionSlideAdapter(promotion_imgs);
        Promotion_viewpager.setAdapter(promotionAdapter);

        hotHotelAdapter = new HomeHotHotelAdapter(this.context,modelArrayList);
//        HotHotel_viewpager.setAdapter(hotHotelAdapter);

        ProcessViewPager(Promotion_viewpager,false);
        ProcessViewPager(HotDeal_viewpager,true);
        ProcessViewPager(Special_viewpager,true);
//        ProcessViewPager(HotHotel_viewpager,true);
//        HotHotel_viewpager.setOffscreenPageLimit(5);
//        HotHotel_viewpager.setUserInputEnabled(false);
    }
    private void SetSmallCard(View view,Hotel item){
        TextView title = view.findViewById(R.id.home_smallcard_name);
        TextView TvRate = (TextView) view.findViewById(R.id.home_smallcard_rate);
        TextView TvNumRate = (TextView) view.findViewById(R.id.home_smallcard_numrate);
        ImageView IvImg = (ImageView) view.findViewById(R.id.home_smallcard_img);

        title.setText(item.getName());
        TvRate.setText(String.valueOf(item.getRate()));
        TvNumRate.setText(String.valueOf(item.getNum_rate()));
        IvImg.setImageResource(item.getImage());
    }
    private void SetNormalCard(View view,Hotel item){
        TextView title = view.findViewById(R.id.home_norcard_name);
        TextView TvRate = (TextView) view.findViewById(R.id.home_norcard_rate);
        TextView TvNumRate = (TextView) view.findViewById(R.id.home_normal_numrate);
        ImageView IvImg = (ImageView) view.findViewById(R.id.home_norcard_img);
        TextView TvPrice = (TextView)view.findViewById(R.id.home_norcard_price);
        TextView TvPromotion = (TextView)view.findViewById(R.id.home_norcard_promotion);

        title.setText(item.getName());
        TvRate.setText(String.valueOf(item.getRate()));
        TvNumRate.setText(String.valueOf(item.getNum_rate()));
        IvImg.setImageResource(item.getImage());
        TvPrice.setText(String.valueOf(item.getPrice()));
        TvPromotion.setText(item.getPromotion());
    }
    private void ProcessViewPager(ViewPager2 viewPager2,boolean showNext){
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(1);
        int pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.card_margin);
        CompositePageTransformer transformer = new CompositePageTransformer();
        if (showNext){

            int peekMarginPx = getResources().getDimensionPixelOffset(R.dimen.peek_offset_margin);



            RecyclerView rv = (RecyclerView) viewPager2.getChildAt(0);
            rv.setClipToPadding(false);
            int padding = peekMarginPx + pageMarginPx;
            rv.setPadding(0, 0, padding, 0);
            transformer.addTransformer(new MarginPageTransformer(pageMarginPx));


        }

        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 -Math.abs(position);
                page.setScaleY(0.8f + v*0.2f);
            }
        });
        viewPager2.setPageTransformer(transformer);

    }
}