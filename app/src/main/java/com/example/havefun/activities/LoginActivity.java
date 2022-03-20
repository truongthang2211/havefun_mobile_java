package com.example.havefun.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import com.example.havefun.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView username = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.pass);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.log_btn);
        //admin and admin
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //correct
                    Toast.makeText(LoginActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                } else
                    //incorrect
                    Toast.makeText(LoginActivity.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}