package com.example.administrator.lazyweekend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.lazyweekend.ChatFragment.ChatFragment;
import com.example.administrator.lazyweekend.MainFragment.MainFragment;
import com.example.administrator.lazyweekend.MineFragment.MineFragment;
import com.example.administrator.lazyweekend.SearchFragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private RadioGroup mRg;
    private MainFragment mainFragment;
    private SearchFragment searchFragment;
    private ChatFragment chatFragment;
    private MineFragment mineFragment;
    private FragmentManager manager;
    private MyPagerAdapter myPagerAdapter;
    private SearchFragment fragment;
    private ViewPager mVp;
    private boolean isExit=false;
    private String cityName;
    private List<Fragment>fragmentList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRadioGroup();
        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        mVp= (ViewPager) findViewById(R.id.main_view_pager);
        initIntent();
        initFragment();
        mVp.setAdapter(myPagerAdapter);
        mVp.setCurrentItem(0);
        mRg.check(R.id.main_radio_btn_first);
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                  switch (position){
                      case 0:
                          mRg.check(R.id.main_radio_btn_first);
                          break;
                      case 1:
                          mRg.check(R.id.main_radio_btn_second);
                          break;
                      case 2:
                          mRg.check(R.id.main_radio_btn_third);
                          break;
                      case 3:
                          mRg.check(R.id.main_radio_btn_fourth);
                          break;
                  }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIntent() {
        Intent intent = getIntent();
         cityName= intent.getStringExtra("cityName");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
                isExit=false;
        }
    };
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次,离开懒人周末",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
    private void initFragment() {
        fragment=SearchFragment.newInstance();
        manager=getSupportFragmentManager();
        mainFragment=MainFragment.newInstance();
        searchFragment=SearchFragment.newInstance();
        chatFragment=ChatFragment.newInstance();
        mineFragment=MineFragment.newInstance();
        fragmentList.add(mainFragment);
        fragmentList.add(searchFragment);
        fragmentList.add(chatFragment);
        fragmentList.add(mineFragment);
        if(cityName!=null){
        searchFragment.changeTxt(cityName);
        }
    }
    private void initRadioGroup(){
        mRg= (RadioGroup) findViewById(R.id.main_radio_group);
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                 switch (i){
                     case R.id.main_radio_btn_first:
                         mVp.setCurrentItem(0);
                         break;
                     case R.id.main_radio_btn_second:
                         mVp.setCurrentItem(1);
                         break;
                     case R.id.main_radio_btn_third:
                         mVp.setCurrentItem(2);
                         break;
                     case R.id.main_radio_btn_fourth:
                         mVp.setCurrentItem(3);
                         break;
                 }
            }
        });
    }
    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList==null?0:fragmentList.size();
        }
    }
}
