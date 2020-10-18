package com.example.daily;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.format.Time;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.UriUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;


public class AddInfoActivity extends AppCompatActivity {
    //定义对象
    EditText et_title,et_content;
    Button btn_save,btn_cancel;
    ImageView iv_pic;
    FloatingActionButton fbtn_pic,fbtn_camera;
    MyDbHelper myDbHelper;
    SQLiteDatabase db;
    String path,path1;
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_addinfo);
        
        //1.绑定控件
        initView();

        //2.把信息保存到数据库中
        btn_save();
        btnOnClick();



    }


    private void initView() {
        et_title=findViewById(R.id.et_title);
        et_content=findViewById(R.id.et_content);
        btn_save=findViewById(R.id.btn_save);
        btn_cancel=findViewById(R.id.btn_cancel);
        iv_pic=findViewById(R.id.iv_pic);
        fbtn_pic=findViewById(R.id.fbtn_pic);
        fbtn_camera=findViewById(R.id.fbtn_camera);
        myDbHelper=new MyDbHelper(AddInfoActivity.this);
        db=myDbHelper.getWritableDatabase();
    }
    //拍照
    private void btnOnClick() {
        fbtn_camera.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Time t =new Time();
                t.setToNow();
                String name = t.year+(t.month+1)+t.monthDay+t.hour+t.minute+t.second+"";
                path = Environment.getExternalStorageDirectory()+"/image"+name+".jpg";
                File f=new File(path);
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent,11);


            }
        });

        fbtn_pic.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent=new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent,22);
            }
        });

    }
    //回调，接受图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                if (resultCode == RESULT_OK) {
                    path1 = path;
                    Glide.with(AddInfoActivity.this).load(path1).into(iv_pic);
                }
                break;
            case 22:
                Uri u = data.getData();
                if (u == null) {

                    return;
                }
                path1 = UriUtils.uri2File(u).getPath();
                Glide.with(AddInfoActivity.this).load(path1).into(iv_pic);
                break;
            default:
                break;
        }

    }




    private void btn_save() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存信息到数据库代码
                Time time=new Time();
                time.setToNow();
                ContentValues contentValues=new ContentValues();//一行
                contentValues.put("title",et_title.getText().toString());//1行——1列
                contentValues.put("content",et_content.getText().toString());//1行——3列
                contentValues.put("mtime",time.year+"/"+(time.month+1)+"/"+time.monthDay);
                contentValues.put("imgpath",path1);
                db.insert("tb_daily",null,contentValues);
                Toast.makeText(AddInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();


                //跳转到主界面
                Intent intent=new Intent(AddInfoActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
