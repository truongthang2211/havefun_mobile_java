package com.example.havefun.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havefun.R;
import com.example.havefun.adapters.HomeHotHotelAdapter;
import com.example.havefun.adapters.HotDealSildeAdapter;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Order;
import com.example.havefun.models.Promotion;
import com.example.havefun.utils.MySingleton;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyOrderActivity extends AppCompatActivity {
    ImageView back;
    LinearLayout listOrdersLinear;
    ArrayList<Order> orderlist;
    Context context;
    String ServerURL;
    TextView status;
    TextView roomid ;
    TextView orderTime ;
    TextView JoinTime ;
    TextView LeftTime;
    TextView price;
    MaterialButton cancel_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        back = findViewById(R.id.myorder_back_ImgV);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listOrdersLinear = findViewById(R.id.myorder_listorder_linear);
        context = this;
        LoadOrders();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setOrderToCard(View view, Order order) {
         status = view.findViewById(R.id.Order_status_Tv);
         roomid = view.findViewById(R.id.Order_Room_Tv_e);
         orderTime = view.findViewById(R.id.Order_orderTime_Tv_e);
         JoinTime = view.findViewById(R.id.Order_Jointime_Tv_e);
         LeftTime = view.findViewById(R.id.Order_Leftime_Tv_e);
         price = view.findViewById(R.id.Order_price_Tv);
         cancel_btn = view.findViewById(R.id.Order_Cancel_Button);

        roomid.setText(order.getRoom().getRoom_id() + " - "+ order.getHotel().getName());
        orderTime.setText(order.getCreated_at().toString());
        JoinTime.setText(order.getOrder_start().toString());
        LeftTime.setText(order.getOrder_end().toString());
        NumberFormat currencyFormatter = NumberFormat.getInstance(new Locale("en", "EN"));

        if (order.getOrder_status().equals("processing")){
            status.setText("Chờ thanh toán");
            price.setText(currencyFormatter.format(order.getTotal_price_estimate())+" đ");
            if (order.getOrder_start().toLocalDateTime().plusHours(1).isBefore(LocalDateTime.now())){
                cancel_btn.setVisibility(View.GONE);
            }
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Bạn có muốn hủy đơn đặt phòng này?")
                            .setConfirmText("Hủy!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    UpdateOrder("canceled", order.getId());

                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            });
        }else if (order.getOrder_status().equals("complete")){
            status.setText("Đã thanh toán");
            price.setText(currencyFormatter.format(order.getTotal_price_estimate())+" đ");
            status.setTextColor(Color.parseColor("#a0ffa0"));
            cancel_btn.setVisibility(View.GONE);
        }else{
            status.setText("Đã bị hủy");
            price.setText("0 đ");
            status.setTextColor(Color.parseColor("#ff2337"));
            cancel_btn.setVisibility(View.GONE);
        }
    }

    private void LoadOrders() {
        ServerURL = getString(R.string.server_address);
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("User", 0);
        String userObjStr = pref.getString("userObject", "undefined");
        if (userObjStr.equals("undefined")) {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Có lỗi về xác thực tài khoản, hãy thử đăng nhập lại.")
                    .show();
            return;
        }
        JSONObject userobj = null;
        try {
            userobj = new JSONObject(userObjStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String OrderURL = null;
        try {
            OrderURL = ServerURL + "/api/orders/user/"+userobj.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, OrderURL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int status = response.getInt("status");

                    if (status == 200) {
                        LayoutInflater inflater = LayoutInflater.from(context);
                        orderlist = new ArrayList<>();
                        JSONArray listorder = response.getJSONArray("data");
                        for (int i = 0 ; i< listorder.length();++i){
                            orderlist.add(new Gson().fromJson(listorder.getJSONObject(i).toString(),Order.class));
                        }
                        for (Order o : orderlist){
                            View view = inflater.inflate(R.layout.order_card_item,listOrdersLinear,false);
                            setOrderToCard(view, o);
                            listOrdersLinear.addView(view);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.i("apiapi", error.toString());
            }
        });
        MySingleton.getInstance(this.context).addToRequestQueue(request);
    }
    private void UpdateOrder(String order_status, String orderId) {
        String orderURL = ServerURL + "/api/orders/update";
        JSONObject putOrder = new JSONObject();
        try {
            putOrder.put("order_status", order_status);
            putOrder.put("id", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, orderURL, putOrder, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int statuss = response.getInt("status");

                    if (statuss == 200) {
                        status.setText("Đã bị hủy");
                        price.setText("0 đ");
                        status.setTextColor(Color.parseColor("#ff2337"));
                        cancel_btn.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.i("apiapi", error.toString());
            }
        });
        MySingleton.getInstance(this.context).addToRequestQueue(request);
    }
}