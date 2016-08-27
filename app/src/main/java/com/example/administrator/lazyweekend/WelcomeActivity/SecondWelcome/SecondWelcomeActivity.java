package com.example.administrator.lazyweekend.WelcomeActivity.SecondWelcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.lazyweekend.MainActivity;
import com.example.administrator.lazyweekend.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/27.
 */
public class SecondWelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private List<Integer>imgList=new ArrayList<>();
    private List<String>bigTxtList=new ArrayList<>();
    private List<String>smallTxtList=new ArrayList<>();
    private MyAdapter myAdapter;
    private Context context;
    private boolean isTouch=false;
    @BindView(R.id.welcome_view_oager)
    ViewPager viewPager;
   @BindView(R.id.welcoe_weibo_btn)
    Button weiboBtn;
    @BindView(R.id.welcome_weixin_btn)
    Button weixinBtn;
    @BindView(R.id.welcome_txt)
    TextView loginTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        context=this;
        ButterKnife.bind(this);
        initData();
        initLisener();
        initAdapter();
        mHandler.sendEmptyMessageDelayed(1,2000);
    }

    private void initLisener() {
        loginTxt.setOnClickListener(this);
    }

    private void initAdapter() {
        myAdapter=new MyAdapter();
        viewPager.setAdapter(myAdapter);
        viewPager.setPageTransformer(true,new AlphaPageTransformer());
        viewPager.setCurrentItem(1000 * imgList.size());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeMessages(1);
                        isTouch=true;
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(1,2000);
                        isTouch=false;
                        break;
                }

                return false;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                       if(isTouch){
                           isTouch=false;
                           mHandler.sendEmptyMessageDelayed(1,2000);
                       }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        imgList.add(R.drawable.pic1);
        imgList.add(R.drawable.pic2);
        imgList.add(R.drawable.pic3);
        imgList.add(R.drawable.pic4);
        bigTxtList.add("闭目");
        bigTxtList.add("睁眼");
        bigTxtList.add("启程");
        bigTxtList.add("我们");
        smallTxtList.add("难掩喜悦与期待");
        smallTxtList.add("因为你心为所动");
        smallTxtList.add("只因追寻你所爱");
        smallTxtList.add("做最了解你的人");
    }
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            mHandler.sendEmptyMessageDelayed(1,2000);
        }
    };
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this, ThirdWelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view= LayoutInflater.from(context).inflate(R.layout.welcome_view_pager_item,container,false);
            ViewHolder viewHolder=new ViewHolder(view);
            viewHolder.img.setImageResource(imgList.get(position%imgList.size()));
            viewHolder.bigTxt.setText(bigTxtList.get(position%bigTxtList.size()));
            viewHolder.smallTxt.setText(smallTxtList.get(position%smallTxtList.size()));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        class ViewHolder {
            @BindView(R.id.welcome_view_pager_img)
            ImageView img;
            @BindView(R.id.welcome_view_pager_bigtxt)
            TextView bigTxt;
            @BindView(R.id.welcome_view_pager_smalltxt)
            TextView smallTxt;
            public ViewHolder(View view){
                ButterKnife.bind(this,view);
            }
        }
    }
}
