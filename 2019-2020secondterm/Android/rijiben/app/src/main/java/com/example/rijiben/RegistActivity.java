package com.example.daily;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistActivity extends AppCompatActivity {
    private EditText et_name, et_pwd;
    private Button btnRegist;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        btnRegist = (Button) findViewById(R.id.btnRegist);
        et_name = (EditText) findViewById(R.id.et_pwd);
        et_pwd = (EditText) findViewById(R.id.et_name);
    }
}