package com.example.daily;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class SPSaveInfo {
    //保存张合和密码到data.xml文件中
    public static boolean saveUserInfo(Context context,String name,String pwd,boolean status){
        //保存用户及密码
        SharedPreferences sp=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sp.edit();//获取编辑对象
        edit.putString("name",name);
        edit.putString("pwd",pwd);
        edit.putBoolean("status", status);
        edit.commit();//提交
        return true;
    }

    //从data.xml文件中获取存储的账号和密码
    public static Map<String,String> getUserInfo(Context context){

        //读取登录信息
        SharedPreferences sp=context.getSharedPreferences("data",Context.MODE_PRIVATE);//读取
        // 第一个参数是key,第二个参数是defValue
        String name=sp.getString("name",null);
        String pwd=sp.getString("pwd",null);
        Boolean status=sp.getBoolean("status",false);
        Map<String,String> userMap=new HashMap<String,String>();
        if(status==true) {
            userMap.put("name", name);//每次加载的时候向文件读取信息
            userMap.put("pwd", pwd);
            userMap.put("status", "false");
        }else{
            userMap.put("name", null);//每次加载的时候向文件读取信息
            userMap.put("pwd", null);
            userMap.put("status", "true");
        }
        return userMap;
    }
}
