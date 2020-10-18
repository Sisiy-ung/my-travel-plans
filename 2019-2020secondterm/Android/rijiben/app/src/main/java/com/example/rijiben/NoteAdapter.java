package com.example.daily;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context mcontext;//上下文环境
    private List<Note> arr1;
    private MyDbHelper mhelper1;
    private SQLiteDatabase db;


    public NoteAdapter(Context mcontext, List<Note> arr1) {
        this.mcontext = mcontext;
        this.arr1 = arr1;
    }

    //负责加载item布局（viewholder是一个缓存，是来放控件和布局的）
    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.rv_item,parent,false);
        ViewHolder mholder=new ViewHolder(view);
        return mholder;

    }

    //负责加载item的数据
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder mholder,final int i) {
        final Note note=arr1.get(i);
        mholder.item_title.setText(note.getTitle());
        mholder.item_content.setText(note.getContent());
        mholder.item_time.setText(note.getTime());
        Glide.with(mcontext).load(note.getPath()).into(mholder.item_img);

        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);//形状
        gradientDrawable.setCornerRadius(10f);//设置圆角Radius
        gradientDrawable.setColor(color);//颜色
       mholder.item_layout.setBackground(gradientDrawable);//设置为background


        //完善：单击其中的一个子项，弹出删除功能
       mholder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出对话框，删除
                AlertDialog.Builder dialog=new AlertDialog.Builder(mcontext);
                dialog.setMessage("确定删除吗？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int abc) {
                        //从数据库当中删除掉
                        mhelper1= new MyDbHelper(mcontext);
                        db=mhelper1.getWritableDatabase();
                        db.delete("tb_daily","title=?",new String[]{arr1.get(i).getTitle()});
                        arr1.remove(i);
                        notifyItemRemoved(i);
                        dialogInterface.dismiss();

                    }
                });
                dialog.setNegativeButton("取消",null);
                dialog.setCancelable(false);
                dialog.create();
                dialog.show();

            }
        });

    }

    //
    @Override
    public int getItemCount() {
        return arr1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView item_title,item_content,item_time;
        ImageView item_img;
        LinearLayout rv_item;
        LinearLayout item_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title=itemView.findViewById(R.id.item_title);
            item_content=itemView.findViewById(R.id.item_content);
            item_time=itemView.findViewById(R.id.item_time);
            item_img=itemView.findViewById(R.id.item_image);
            rv_item=itemView.findViewById(R.id.rv_item);
            item_layout=itemView.findViewById(R.id.item_layout);


        }
    }
}
