package com.example.daily;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;



import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText et_name, et_pwd;
    private Button btnLogin;
    private Button btnRegist;
    private CheckBox ckb_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化界面
        initView();

        //2.点击登录按钮，把用户名和密码保存起来

        btnloginonClick();
        btnregistClick();


        //3.显示用户名和密码
        displayinfo();

    }


    private void initView() {

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegist = (Button) findViewById(R.id.btnRegist);
        et_name = (EditText) findViewById(R.id.et_pwd);
        et_pwd = (EditText) findViewById(R.id.et_name);

        ckb_status = findViewById(R.id.ckb_status);

        //设置按钮的点击事件

    }

    public void btnloginonClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当点击“登录”按钮时，获取密码和账号
                SharedPreferences.Editor editor = getSharedPreferences("myinfo", 0).edit();
                editor.putString("name", et_name.getText().toString());
                editor.putString("pwd", et_pwd.getText().toString());
                editor.putBoolean("st", ckb_status.isChecked());
                editor.commit();


                //检测账号和密码是否正确

                if (TextUtils.isEmpty(et_name.getText())) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_pwd.getText())) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }

                //登录成功
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });

    }

    //3.下次启动，直接显示用户名和密码


    private void displayinfo() {
        String strname = getSharedPreferences("myinfo", 0).getString("name", "");
        String strpwd = getSharedPreferences("myinfo", 0).getString("pwd", "");
        Boolean status = getSharedPreferences("myinfo", 0).getBoolean("st", false);
        if (status == true) {
            et_name.setText(strname);
            et_pwd.setText(strpwd);
            ckb_status.setChecked(true);
        } else {
            et_name.setText("");
            et_pwd.setText("");
            ckb_status.setChecked(false);
        }
    }

    private void btnregistClick() {
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
            }

        });
    }
}