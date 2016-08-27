package com.example.d19_homework;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup mGroup;
    private Fragment mCurrentFragment;
    private MyGuideFragment mGuideFragment;
    private MyHotFragment mHotFragment;
    private MyCategeryFragment mCategeryFragment;
    private MyMineFragment mMineFragment;
    private FrameLayout mFrameLayout;
    private FragmentManager mManager;
    private ViewPager mPager;
    private MyAdapter mAdapter;
    private List<Fragment>fragmentList=new ArrayList<>();
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        initFragment();
        initFragmentData();
        mManager=getSupportFragmentManager();
        mFrameLayout= (FrameLayout) findViewById(R.id.main_frame_layout);
        mGroup= (RadioGroup) findViewById(R.id.main_bottom_radio_group);
        mPager= (ViewPager) findViewById(R.id.main_viewpager_layout);
        mAdapter=new MyAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        chooseFragment(mGuideFragment);
        mGroup.check(R.id.main_bottom_guide_rb);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.main_bottom_guide_rb:
                        chooseFragment(mGuideFragment);
                        break;
                    case R.id.main_bottom_hot_rb:
                        chooseFragment(mHotFragment);
                        break;
                    case R.id.main_bottom_category_rb:
                        chooseFragment(mCategeryFragment);
                        break;
                    case R.id.main_bottom_mine_rb:
                        chooseFragment(mMineFragment);
                        break;
                }
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        //mCurrentFragment=mGuideFragment;
                        mGroup.check(R.id.main_bottom_guide_rb);
                        break;
                    case 1:
                        //mCurrentFragment=mHotFragment;
                        mGroup.check(R.id.main_bottom_hot_rb);
                        break;
                    case 2:
                        //mCurrentFragment=mCategeryFragment;
                        mGroup.check(R.id.main_bottom_category_rb);
                        break;
                    case 3:
                        //mCurrentFragment=mMineFragment;
                        mGroup.check(R.id.main_bottom_mine_rb);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initFragmentData() {
        fragmentList.add(mGuideFragment);
        fragmentList.add(mHotFragment);
        fragmentList.add(mCategeryFragment);
        fragmentList.add(mMineFragment);
    }

    private void initFragment() {
        mGuideFragment=MyGuideFragment.newInstance();
        mHotFragment=MyHotFragment.newInstance();
        mCategeryFragment=MyCategeryFragment.newInstance();
        mMineFragment=MyMineFragment.newInstance();
    }
    private void chooseFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        if(mCurrentFragment!=null){
            fragmentTransaction.hide(mCurrentFragment);
        }
        if(fragment.isAdded()){
            fragmentTransaction.show(fragment);
        }else{
            fragmentTransaction.add(R.id.main_frame_layout,fragment);
        }
        fragmentTransaction.commit();
        mCurrentFragment=fragment;
    }
   class MyAdapter extends FragmentPagerAdapter{

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList==null? 0:fragmentList.size();
    }
}
}

