package com.example.havefun.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havefun.R;
import com.example.havefun.activities.HotelDetailActivity;
import com.example.havefun.activities.SearchActivity;
import com.example.havefun.activities.ShowMoreActivity;
import com.example.havefun.adapters.HomeHotHotelAdapter;
import com.example.havefun.adapters.HotDealSildeAdapter;
import com.example.havefun.adapters.PromotionSlideAdapter;
import com.example.havefun.databinding.FragmentHomeBinding;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Promotion;
import com.example.havefun.utils.MySingleton;
import com.example.havefun.viewmodels.HomeViewModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private ViewPager2 Promotion_viewpager;
    private ViewPager2 HotDeal_viewpager;
    private ViewPager2 Special_viewpager;
    private ArrayList<Hotel> modelArrayList;
    private HotDealSildeAdapter hotDealAdapter;
    private HotDealSildeAdapter specialAdapter;
    private PromotionSlideAdapter promotionAdapter;
    private LinearLayout linearHotHotel;
    private LinearLayout linearTopHotel;
    private LinearLayout linearMoreHotel;
    private FragmentHomeBinding binding;
    private Context context;
    private ImageButton search_btn;
    ArrayList<Promotion> promotionsList;


    private TextView home_tv_morehotdeal;
    private TextView home_tv_morespecial;
    private TextView home_tv_morehothotel;
    private TextView home_tv_moretoprate;
    private TextView home_tv_moremorehotel;

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

        linearHotHotel = getView().findViewById(R.id.home_linear_hothotel);
        linearTopHotel = getView().findViewById(R.id.home_linear_toprate);
        linearMoreHotel = getView().findViewById(R.id.home_linear_morehotel);
        search_btn = getView().findViewById(R.id.home_imgbutton_search);
        home_tv_morehotdeal = getView().findViewById(R.id.home_tv_morehotdeal);
        home_tv_morespecial = getView().findViewById(R.id.home_tv_morespecial);
        home_tv_morehothotel = getView().findViewById(R.id.home_tv_morehothotel);
        home_tv_moretoprate = getView().findViewById(R.id.home_tv_moretoprate);
        home_tv_moremorehotel = getView().findViewById(R.id.home_tv_moremorehotel);
        loadCard();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadCard() {
        String ServerURL = getString(R.string.server_address);
        String HotelURL = ServerURL + "/api/hotels";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, HotelURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");

                    if (status == 200) {
                        modelArrayList = new ArrayList<>();
                        JSONArray hotels = response.getJSONArray("data");
                        search_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("listHotel", hotels.toString());
                                startActivity(intent);
                            }
                        });
                        home_tv_morehotdeal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ShowMoreActivity.class);
                                intent.putExtra("listHotel", hotels.toString());
                                intent.putExtra("TypeTitle", "Giá sốc đêm nay");
                                startActivity(intent);
                            }
                        });
                        home_tv_morespecial.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ShowMoreActivity.class);
                                intent.putExtra("listHotel", hotels.toString());
                                intent.putExtra("TypeTitle", "Ưu đãi đặc biệt");
                                startActivity(intent);
                            }
                        });
                        home_tv_morehothotel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ShowMoreActivity.class);
                                intent.putExtra("listHotel", hotels.toString());
                                intent.putExtra("TypeTitle", "Khách sạn mới");
                                startActivity(intent);
                            }
                        });
                        home_tv_moretoprate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ShowMoreActivity.class);
                                intent.putExtra("listHotel", hotels.toString());
                                intent.putExtra("TypeTitle", "Top được bình chọn");
                                startActivity(intent);
                            }
                        });
                        home_tv_moremorehotel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ShowMoreActivity.class);
                                intent.putExtra("listHotel", hotels.toString());
                                intent.putExtra("TypeTitle", "Khám phá thêm");
                                startActivity(intent);
                            }
                        });

                        for (int i = 0; i < hotels.length(); ++i) {
                            Hotel hotel = new Gson().fromJson(hotels.getJSONObject(i).toString(), Hotel.class);
                            modelArrayList.add(hotel);
                        }
                        LayoutInflater inflater = LayoutInflater.from(context);
                        Collections.sort(modelArrayList, new Comparator<Hotel>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public int compare(Hotel lhs, Hotel rhs) {
                                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                                return lhs.getCreated_at().toLocalDateTime().isBefore(rhs.getCreated_at().toLocalDateTime()) ? 1 : lhs.getCreated_at().toLocalDateTime().isEqual(rhs.getCreated_at().toLocalDateTime()) ? 0 : -1;
                            }
                        });
                        for (Hotel item : modelArrayList) {
                            View view = inflater.inflate(R.layout.home_small_card, linearHotHotel, false);
                            View view3 = inflater.inflate(R.layout.home_normal_card, linearTopHotel, false);
                            // set item content in view
                            SetSmallCard(view, item);
                            SetNormalCard(view3, item);
                            linearHotHotel.addView(view);
                            linearMoreHotel.addView(view3);
                        }
                        Collections.sort(modelArrayList, new Comparator<Hotel>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public int compare(Hotel a, Hotel b) {
                                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                                return a.getAvgStar() > b.getAvgStar() ? -1 : a.getAvgStar() < b.getAvgStar() ? 1 : 0;
                            }
                        });
                        for (Hotel item : modelArrayList) {
                            View view2 = inflater.inflate(R.layout.home_small_card, linearTopHotel, false);
                            // set item content in view
                            SetSmallCard(view2, item);
                            linearTopHotel.addView(view2);
                        }

                        ArrayList<Hotel> hotedeal_thisnight = new ArrayList<>();
                        for (Hotel i : modelArrayList) {
                            Promotion iPromotes[] = i.getPromotions();
                            for (Promotion p : iPromotes) {
                                if (p.getOrder_type() != null)
                                    for (String t : p.getOrder_type()) {
                                        if (t.equals("overnight")) {
                                            hotedeal_thisnight.add(i);
                                            break;
                                        }
                                    }
                            }
                        }
                        hotDealAdapter = new HotDealSildeAdapter(context, hotedeal_thisnight, true);
                        HotDeal_viewpager.setAdapter(hotDealAdapter);

                        specialAdapter = new HotDealSildeAdapter(context, modelArrayList, false);
                        Special_viewpager.setAdapter(specialAdapter);




                        ProcessViewPager(HotDeal_viewpager, true);
                        ProcessViewPager(Special_viewpager, true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.i("api", error.toString());
            }
        });
        String promotionURL = ServerURL + "/api/promotions";
        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, promotionURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    promotionsList = new ArrayList<>();
                    JSONArray promotionsJS = response.getJSONArray("data");
                    for (int i = 0; i < promotionsJS.length(); ++i) {
                        Promotion promotion = new Gson().fromJson(promotionsJS.getJSONObject(i).toString(), Promotion.class);
                        promotionsList.add(promotion);
                    }
                    TextView promotion_pos = getView().findViewById(R.id.home_tv_slidepos);
                    promotion_pos.setText(1 + "/" + promotionsList.size());

                    promotionAdapter = new PromotionSlideAdapter(context, promotionsList);
                    Promotion_viewpager.setAdapter(promotionAdapter);
                    ProcessViewPager(Promotion_viewpager, false);
                    Promotion_viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageScrollStateChanged(int state) {
                            super.onPageScrollStateChanged(state);
                            promotion_pos.setText(Promotion_viewpager.getCurrentItem() +1+ "/" + promotionsList.size());
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.i("api", error.toString());
            }
        });
        MySingleton.getInstance(this.context).addToRequestQueue(request);
        MySingleton.getInstance(this.context).addToRequestQueue(request2);


//        ProcessViewPager(HotHotel_viewpager,true);
//        HotHotel_viewpager.setOffscreenPageLimit(5);
//        HotHotel_viewpager.setUserInputEnabled(false);
    }

    private void SetSmallCard(View view, Hotel item) {
        TextView title = view.findViewById(R.id.home_smallcard_name);
        TextView TvRate = (TextView) view.findViewById(R.id.home_smallcard_rate);
        TextView TvNumRate = (TextView) view.findViewById(R.id.home_smallcard_numrate);
        ImageView IvImg = (ImageView) view.findViewById(R.id.home_smallcard_img);

        title.setText(item.getName());
        if (item.getRatings() != null) {
            TvNumRate.setText(String.valueOf(item.getRatings().length));
            TvRate.setText(String.valueOf(item.getAvgStar()));

        }
        Picasso.get().load(item.getImgs()[0]).into(IvImg);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HotelDetailActivity.class);
                intent.putExtra("hotel",new Gson().toJson(item));
                context.startActivity(intent);
            }
        });
    }

    private void SetNormalCard(View view, Hotel item) {
        TextView title = view.findViewById(R.id.home_norcard_name);
        TextView TvRate = (TextView) view.findViewById(R.id.home_norcard_rate);
        TextView TvNumRate = (TextView) view.findViewById(R.id.home_normal_numrate);
        ImageView IvImg = (ImageView) view.findViewById(R.id.home_norcard_img);
        TextView TvPrice = (TextView) view.findViewById(R.id.home_norcard_price);
        TextView TvPromotion = (TextView) view.findViewById(R.id.home_norcard_promotion);

        title.setText(item.getName());
        TvPromotion.setVisibility(View.INVISIBLE);
        if (item.getRatings() != null) {
            TvNumRate.setText(String.valueOf(item.getRatings().length));
            TvRate.setText(String.valueOf(item.getAvgStar()));
        }
        Picasso.get().load(item.getImgs()[0]).into(IvImg);
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("en", "EN"));
        TvPrice.setText(currencyFormatter.format(item.getRooms()[0].getHour_price()) + " đ");
        if (item.getPromotions().length > 0) {
            TvPromotion.setVisibility(View.VISIBLE);
            TvPromotion.setText(String.valueOf(currencyFormatter.format(item.getPromotions()[0].getDiscount_ratio() * 100)) + "%");
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HotelDetailActivity.class);
                intent.putExtra("hotel",new Gson().toJson(item));
                context.startActivity(intent);
            }
        });
    }

    private void ProcessViewPager(ViewPager2 viewPager2, boolean showNext) {
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(1);
        int pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.card_margin);
        CompositePageTransformer transformer = new CompositePageTransformer();
        if (showNext) {
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
                float v = 1 - Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
            }
        });
        viewPager2.setPageTransformer(transformer);
    }
}