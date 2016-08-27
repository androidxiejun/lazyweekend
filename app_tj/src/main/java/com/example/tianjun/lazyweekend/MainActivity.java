package com.example.tianjun.lazyweekend;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tianjun.lazyweekend.util.CustomerView.AlphaPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener,ViewPager.OnPageChangeListener {
    @BindView(R.id.welcome_pager)
    ViewPager mWelcomePager;

    private static Boolean isTouch = false;
    private List<Integer> imgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        loadData();
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        WelcomePagerAdapter welcomePagerAdapter = new WelcomePagerAdapter();
        mWelcomePager.setAdapter(welcomePagerAdapter);
        //自定义滑动效果
        mWelcomePager.setPageTransformer(true,new AlphaPageTransformer());
        mWelcomePager.setOnTouchListener(this);
        mWelcomePager.setOnPageChangeListener(this);


        mWelcomePager.setCurrentItem(1000 * imgList.size());

        autoScrollHandler.sendEmptyMessageDelayed(1,2000);
    }

    private void loadData() {
        imgList = new ArrayList<>();
        imgList.add(R.drawable.pic1);
        imgList.add(R.drawable.pic2);
        imgList.add(R.drawable.pic3);
        imgList.add(R.drawable.pic4);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(isTouch){
            isTouch = false;
            autoScrollHandler.sendEmptyMessageDelayed(1,2000);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                autoScrollHandler.removeMessages(1);
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                autoScrollHandler.sendEmptyMessageDelayed(1,2000);
                break;
        }
        return false;
    }

    class WelcomePagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(imgList.get(position % imgList.size()));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private Handler autoScrollHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mWelcomePager.setCurrentItem(mWelcomePager.getCurrentItem() + 1);
            autoScrollHandler.sendEmptyMessageDelayed(1,2000);
        }
    };
}
