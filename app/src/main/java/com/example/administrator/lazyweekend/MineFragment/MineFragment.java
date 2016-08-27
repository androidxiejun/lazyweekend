package com.example.administrator.lazyweekend.MineFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.lazyweekend.LoginActivity.LoginActivity;
import com.example.administrator.lazyweekend.R;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    private Context context;
    private RadioGroup mRg;
    private RadioButton mRb1,mRb2,mRb3;
    private ImageView mIv;
    public static MineFragment newInstance(){
        return new MineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mine_layout,container,false);
        mRg= (RadioGroup) view.findViewById(R.id.mine_layout_radio_group);
        mRb1= (RadioButton) view.findViewById(R.id.mine_radio_btn_order);
        mRb2= (RadioButton) view.findViewById(R.id.mine_radio_btn_interest);
        mRb3= (RadioButton) view.findViewById(R.id.mine_radio_btn_setting);
        mIv= (ImageView) view.findViewById(R.id.mine_layout_cicle_cat);
        mIv.setOnClickListener(this);
        chooseTab();
        return view;
    }

    private void chooseTab() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.mine_radio_btn_order:
                        Intent intent=new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        mRb1.setChecked(false);
                        break;
                    case R.id.mine_radio_btn_interest:
                        Intent intent1=new Intent(context,InterestActivity.class);
                        intent1.putExtra("name","wangxia");
                        startActivity(intent1);
                        mRb2.setChecked(false);
                        //TODO
                        break;
                    case R.id.mine_radio_btn_setting:
                        //TODO
                        mRb3.setChecked(false);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(context,LoginActivity.class);
        startActivity(intent);
    }
}
