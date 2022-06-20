package com.example.havefun.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havefun.R;
import com.example.havefun.activities.DetailPromotionActivity;
import com.example.havefun.activities.HotelDetailActivity;
import com.example.havefun.databinding.FragmentPromotionBinding;
import com.example.havefun.models.Promotion;
import com.example.havefun.utils.MySingleton;
import com.example.havefun.viewmodels.PromotionViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PromotionFragment extends Fragment {
    private Context context;
    String HotelURL;
    private FragmentPromotionBinding binding;
    private LinearLayout ListVoucherLinear;
    private LinearLayout ListTypeLinear;
    private TextView num_pro;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PromotionViewModel promotionViewModel =
                new ViewModelProvider(this).get(PromotionViewModel.class);

        binding = FragmentPromotionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        this.context = container.getContext();
//        final TextView textView = binding.textDashboard;
//        promotionViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String ServerURL = getString(R.string.server_address);
        HotelURL = ServerURL + "/api/hotels/";

        ListVoucherLinear = getView().findViewById(R.id.promotion_listVoucher_Linear);
        num_pro= getView().findViewById(R.id.promotion_numpre_Tv);
        LoadVoucher();
//        View pro_item = view.findViewById(R.id.pro_item_promote_incl);
//        pro_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(getActivity(), DetailPromotionActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void LoadVoucher(){

        String serverURL = getString(R.string.server_address);
        String promotionsURL = serverURL + "/api/promotions";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, promotionsURL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("status") == 200){
                        JSONArray promotionJS = response.getJSONArray("data");
                        LayoutInflater inflater = LayoutInflater.from(context);
                        num_pro.setText(String.valueOf(promotionJS.length()));
                        for (int i = 0; i< promotionJS.length();++i){
                            Promotion p  = new Gson().fromJson(promotionJS.getJSONObject(i).toString(),Promotion.class);
                            View view  = inflater.inflate(R.layout.voucher_item, ListVoucherLinear, false);
                            SetVoucherCard(view,p);
                            ListVoucherLinear.addView(view);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(this.context).addToRequestQueue(request);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SetVoucherCard(View view, Promotion p){
        TextView title = view.findViewById(R.id.voucher_title_Tv);
        TextView ratio = view.findViewById(R.id.voucher_ratio_Tv);
        TextView hour = view.findViewById(R.id.voucher_byhour_Tv);
        TextView overnight = view.findViewById(R.id.voucher_overnight_Tv);
        TextView daily = view.findViewById(R.id.voucher_daily_Tv);
        TextView start = view.findViewById(R.id.voucher_startTime_Tv);
        TextView end = view.findViewById(R.id.voucher_endtime_Tv);
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("en", "EN"));
        title.setText(p.getName());
        ratio.setText(currencyFormatter.format(p.getDiscount_ratio()*100)+"%");
        if (p.getOrder_type()!=null){
            for (int i = 0 ;i< p.getOrder_type().length;++i){
                if (p.getOrder_type()[i].equals("hour")){
                    hour.setVisibility(View.VISIBLE);
                    hour.setText("Theo giờ");
                }else if (p.getOrder_type()[i].equals("overnight")){
                    overnight.setVisibility(View.VISIBLE);
                    overnight.setText("Qua đêm");
                }else{
                    daily.setVisibility(View.VISIBLE);
                    daily.setText("Theo ngày");
                }
            }
        }
        TextView voucher_use_tv = view.findViewById(R.id.voucher_use_tv);
        voucher_use_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartHotelDetailAct(HotelURL + p.getHotel_id());
            }
        });
        start.setText(p.getTime_start().toString());
        end.setText(p.getTime_end().toString());

    }
    private void StartHotelDetailAct(String hotelURL){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, hotelURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");
                    if (status == 200){
                        JSONObject hotel = response.getJSONObject("data");
                        Intent intent = new Intent(context, HotelDetailActivity.class);
                        intent.putExtra("hotel",hotel.toString());
                        startActivity(intent);
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
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}