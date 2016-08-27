package com.example.administrator.lazyweekend.MineFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.lazyweekend.MainActivity;
import com.example.administrator.lazyweekend.R;
import com.example.administrator.lazyweekend.WelcomeActivity.SecondWelcome.ThirdWelcomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class InterestActivity extends AppCompatActivity implements View.OnClickListener{
    private GridView mGv;
    private Context context;
    private MyAdapter myAdapter;
    private Button backBtn;
    private Button savaBtn;
    private SharedPreferences mSp;
    private String name="";
    private List<CheckBox>boxList=new ArrayList<>();
    private List<Integer> imgList=new ArrayList<>();
    private List<String>txtList=new ArrayList<>();
    private List<Integer>imgUncheckedList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intrest_layout);
        context=this;
        initIntent();
        initData();
        initView();
    }

    private void initIntent() {
        Intent intent = getIntent();
        name=intent.getStringExtra("name");
    }

    private void initData() {
       imgList.add(R.drawable.ic_c_montain);imgUncheckedList.add(R.drawable.ic_c_montain_gray);
       imgList.add(R.drawable.ic_c_bar); imgUncheckedList.add(R.drawable.ic_c_bar_gray);
       imgList.add(R.drawable.ic_c_music);imgUncheckedList.add(R.drawable.ic_c_music_gray);
       imgList.add(R.drawable.ic_c_stage);imgUncheckedList.add(R.drawable.ic_c_stage_gray);
       imgList.add(R.drawable.ic_c_pic); imgUncheckedList.add(R.drawable.ic_c_pic_gray);
       imgList.add(R.drawable.ic_c_eat);imgUncheckedList.add(R.drawable.ic_c_eat_gray);
       imgList.add(R.drawable.ic_c_bag);imgUncheckedList.add(R.drawable.ic_c_bag_gray);
       imgList.add(R.drawable.ic_c_movie);imgUncheckedList.add(R.drawable.ic_c_movie_gray);
       imgList.add(R.drawable.ic_c_persons);imgUncheckedList.add(R.drawable.ic_c_persons_gray);
       imgList.add(R.drawable.ic_c_backetball);imgUncheckedList.add(R.drawable.ic_c_backetball_gray);
       imgList.add(R.drawable.ic_c_leaf);imgUncheckedList.add(R.drawable.ic_c_leaf_gray);
       imgList.add(R.drawable.ic_c_shirt);imgUncheckedList.add(R.drawable.ic_c_shirt_gray);
        txtList.add("周边游");
        txtList.add("酒吧");
        txtList.add("音乐");
        txtList.add("戏剧");
        txtList.add("展览");
        txtList.add("美食");
        txtList.add("购物");
        txtList.add("电影");
        txtList.add("聚会");
        txtList.add("运动");
        txtList.add("公益");
        txtList.add("商业");

    }

    private void initView() {
        mGv= (GridView) findViewById(R.id.intrest_grid_view);
        backBtn= (Button) findViewById(R.id.intrest_back_btn);
        savaBtn= (Button) findViewById(R.id.intrest_save_btn);
        mSp=getSharedPreferences("interest",Context.MODE_PRIVATE);
        backBtn.setOnClickListener(this);
        savaBtn.setOnClickListener(this);
        if(name.equals("xiejun")){
            savaBtn.setBackgroundResource(R.drawable.right);
        }else{
            savaBtn.setText("保存");
            savaBtn.setBackgroundResource(R.color.whiteBar);
        }
        myAdapter=new MyAdapter();
        mGv.setAdapter(myAdapter);
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    @Override
    public void onClick(View view) {
          int viewId=view.getId();
        switch (viewId){
            case R.id.intrest_back_btn:
                if(name.equals("xiejun")){
                    Intent intent=new Intent(this, ThirdWelcomeActivity.class);
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.intrest_save_btn:
                if(name.equals("xiejun")){
                    Intent intent=new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
                break;
               }
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView( int i, View containView, ViewGroup viewGroup) {
            View view=containView;
            ViewHolder viewHolder=null;
            if(view==null){
                view= LayoutInflater.from(context).inflate(R.layout.interst_grid_item,viewGroup,false);
                viewHolder=new ViewHolder(view);
            }else{
                viewHolder= (ViewHolder) view.getTag();
            }
            viewHolder.img.setImageResource(imgUncheckedList.get(i));
            viewHolder.checkTxt.setText(txtList.get(i));
            final CheckBox checkBox=viewHolder.checkTxt;
            boxList.add(checkBox);
            final ViewHolder finalViewHolder = viewHolder;
            final int index=i;
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checkBox.isChecked()){
                        finalViewHolder.img.setImageResource(imgList.get(index));
                        checkBox.setChecked(true);
                    }else{
                        finalViewHolder.img.setImageResource(imgUncheckedList.get(index));
                        checkBox.setChecked(false);
                    }
                }
            });
            viewHolder.checkTxt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                             if(checkBox.isChecked()){
                                 finalViewHolder.img.setImageResource(imgList.get(index));
                             }else{
                                 finalViewHolder.img.setImageResource(imgUncheckedList.get(index));
                             }
                }
            });
            return view;
        }
        class ViewHolder{
            public ImageView img;
            public CheckBox checkTxt;
            public ViewHolder(View view){
                view.setTag(this);
                this.img= (ImageView) view.findViewById(R.id.interst_grid_view_img);
                this.checkTxt= (CheckBox) view.findViewById(R.id.interst_grid_view_txt);
            }
        }

    }
}
