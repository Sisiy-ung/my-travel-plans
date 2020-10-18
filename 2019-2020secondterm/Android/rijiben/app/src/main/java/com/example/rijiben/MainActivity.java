package com.example.daily;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //定义对象
    private FloatingActionButton fbtn_note;
    private FloatingActionButton fbtn_alarm;
    private RecyclerView rv;
    MyDbHelper myDbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //1.绑定控件
        initView();
        //2.对按钮添加单击事件
        fbtnonclicknext();
        //3.从数据库获取数据，显示到rv中
        rvDisplay();
        
    }

    private void initView() {
        fbtn_alarm=findViewById(R.id.fbtn_alarm);
        fbtn_note=findViewById(R.id.fbtn_note);
        rv=findViewById(R.id.rv);
        myDbHelper=new MyDbHelper(MainActivity.this);
        db=myDbHelper.getWritableDatabase();

    }
    private void fbtnonclicknext() {
        fbtn_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void rvDisplay() {
        //1.准备数据---标题、内容、图片、时间（类）
        List<Note> arr=new ArrayList();

        //从数据库里取数据  游标结果集
        Cursor cursor=db.rawQuery("select * from tb_daily",null);
        while(cursor.moveToNext()){
            String mytitle=cursor.getString(cursor.getColumnIndex("title"));
            String mycontent=cursor.getString(cursor.getColumnIndex("content"));
            String myimgpath=cursor.getString(cursor.getColumnIndex("imgpath"));
            String mymtime=cursor.getString(cursor.getColumnIndex("mtime"));


            Note note=new Note(mytitle,mycontent,myimgpath,mymtime);//放到一个类再放到数组
            arr.add(note);
        }
        cursor.close();

        //2.子布局rv_item

        //3.数据->子布局
        NoteAdapter adapter=new NoteAdapter(MainActivity.this,arr);

        //4.确定显示的方式
        StaggeredGridLayoutManager st=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(st);
        //5.让数据显示出来
        rv.setAdapter(adapter);
    }

}
