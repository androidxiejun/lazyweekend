package com.example.administrator.lazyweekend.ChatFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lazyweekend.LoginActivity.LoginActivity;
import com.example.administrator.lazyweekend.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class ChatFragment extends Fragment implements View.OnClickListener{
    private Context context;
    private  TextView loginTxt;
    private ViewPager mVp;
    private ImageView dialogImg;
    private MyPagerAdapter myPagerAdapter;
    private TextView dialogTxt,dialogContent;
    private LinearLayout mLinearLayout;
    private List<Integer>imgList=new ArrayList<>();
    private List<String>txtList=new ArrayList<>();
    private List<String>contentList=new ArrayList<>();
    private int childCount;
    private AlertDialog.Builder builder;
    public static ChatFragment newInstance(){
        return new ChatFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
    }
    private void initTxtData(){
        txtList.add("喵出你想");
        txtList.add("懒喵订票");
        txtList.add("多人团建");
        contentList.add("说出你的周末需求，我们来满足你要的所求");
        contentList.add("欢乐票务我来帮你订，你就是世界的中心");
        contentList.add("专属定制，个性出游尽享惊喜之旅");
    }

    private void initImgData() {
        imgList.add(R.drawable.guide1);
        imgList.add(R.drawable.guide2);
        imgList.add(R.drawable.guide3);
    }
    private void controlIndicator(int index) {
        ImageView view = (ImageView) mLinearLayout.getChildAt(index);
        for (int i = 0; i < childCount; i++) {
            ImageView childView = (ImageView) mLinearLayout.getChildAt(i);
            childView.setEnabled(false);
        }
        view.setEnabled(true);
    }
//    private void initDialog() {
//        builder=new AlertDialog.Builder(context);
//        View view=LayoutInflater.from(context).inflate(R.layout.chat_dialog_layout,null);
//        mVp= (ViewPager) view.findViewById(R.id.chat_dialog_view_pager);
//        myPagerAdapter=new MyPagerAdapter();
//        mVp.setAdapter(myPagerAdapter);
//        builder.setView(view);
//        builder.create().show();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chat_layout,container,false);
        View vview2=inflater.inflate(R.layout.chat_dialog_layout,null);
        loginTxt= (TextView) view.findViewById(R.id.chat_layout_lazy_cat_txt);
        loginTxt.setOnClickListener(this);
        mLinearLayout= (LinearLayout) vview2.findViewById(R.id.chat_dialog_linear_layout);
        childCount=mLinearLayout.getChildCount();
//        initDialog();
        initTxtData();
        initImgData();
        controlIndicator(0);
//        myPagerAdapter.notifyDataSetChanged();
//        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                controlIndicator(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        return view;
    }
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(context, LoginActivity.class);
        startActivity(intent);
    }
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=LayoutInflater.from(context).inflate(R.layout.chat_view_pager_item,container,false);
            dialogImg= (ImageView) view.findViewById(R.id.chat_dialog_img);
            dialogTxt= (TextView) view.findViewById(R.id.chat_dialog_title);
           dialogContent= (TextView) view.findViewById(R.id.chat_dialog_content);
            dialogImg.setImageResource(imgList.get(position));
            dialogTxt.setText(txtList.get(position));
            dialogContent.setText(contentList.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
