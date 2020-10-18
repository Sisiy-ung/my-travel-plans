package com.example.daily;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {
    private static String DbName="daily.db";

    public MyDbHelper(@Nullable Context context) {
        super(context,DbName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tb_memory(_id Integer primary key,title String (200),content String (2000),imgpath String (200),mtime String (50))");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("myDebug","数据库版本已经更新啦");

    }
}
