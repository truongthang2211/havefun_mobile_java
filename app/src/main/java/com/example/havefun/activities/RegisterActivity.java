package com.example.havefun.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havefun.R;
import com.example.havefun.utils.MySingleton;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends Activity {
    EditText email_reg;
    EditText phone_reg;
    EditText password_reg;
    Context context;
    MaterialButton res_log_btn;
    MaterialButton res_resg_btn;
    private boolean isShow= false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        email_reg = findViewById(R.id.email_reg);
        phone_reg = findViewById(R.id.phone_reg);
        password_reg = findViewById(R.id.pass_reg);
        Button eye_p = findViewById(R.id.eye_pass_reg);
        res_resg_btn = findViewById(R.id.res_resg_btn);
        res_log_btn = findViewById(R.id.res_log_btn);
        res_log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(intent);
                    finish();
            }
        });
        res_resg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ServerURL = getString(R.string.server_address);
                String SignUp = ServerURL + "/api/users/signup";
                JSONObject account = new JSONObject();
                try {
                    account.put("email",email_reg.getText());
                    account.put("phone",phone_reg.getText());
                    account.put("password",password_reg.getText());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,SignUp, account, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == 200) {
                                JSONObject dataAccount = response.getJSONObject("data");
                                if (dataAccount != null){
                                    SharedPreferences pref =getApplicationContext().getSharedPreferences("User", 0);
                                    SharedPreferences.Editor edit = pref.edit();
                                    edit.putString("userObject",dataAccount.toString());
                                    edit.apply();
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("result","signup");
                                    setResult(Activity.RESULT_OK,returnIntent);
                                    finish();
                                }
                            }else if (status == 201){
                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Email đã tồn tại")
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
                    password_reg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    eye_p.setSelected(false);
                    isShow= false;
                    //Password not visible
                    password_reg.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}
