package com.example.havefun.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havefun.adapters.HomeHotHotelAdapter;
import com.example.havefun.adapters.HotDealSildeAdapter;
import com.example.havefun.models.Hotel;
import com.example.havefun.models.Promotion;
import com.example.havefun.utils.MySingleton;
import com.google.android.material.button.MaterialButton;

import com.example.havefun.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    Context context;
    public static Activity LoginAct;
    private boolean isShow= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        LoginAct = this;
        Button eye_p = findViewById(R.id.eye_pass);
        TextView username = (TextView) findViewById(R.id.email_sign);
        TextView password = (TextView) findViewById(R.id.pass_sign);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.log_sign_btn);
        MaterialButton resbtn = findViewById(R.id.resg_ign_btn);
        //admin and admin
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ServerURL = getString(R.string.server_address);
                String SignIn = ServerURL + "/api/users/signin";
                JSONObject account = new JSONObject();
                try {
                    account.put("email",username.getText());
                    account.put("password",password.getText());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, SignIn, account, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == 200) {
                                JSONObject dataAccount = response.getJSONObject("data");
                                if (dataAccount != null){
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("User", 0);
                                    SharedPreferences.Editor edit = pref.edit();
                                    edit.putString("userObject",dataAccount.toString());
                                    edit.apply();
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("result","login");
                                    setResult(Activity.RESULT_OK,returnIntent);
                                    finish();
                                }
                            }else if (status == 201){
                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Email hoặc password không hợp lệ")
                                        .show();
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
        });
        eye_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShow) {
                    eye_p.setSelected(true);
                    isShow = true;
                    //Password visible
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    eye_p.setSelected(false);
                    isShow= false;
                    //Password not visible
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        resbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,4);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4) {
            String result=data.getStringExtra("result");
            if (result.equals("signup")) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","login");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        }
    }
}