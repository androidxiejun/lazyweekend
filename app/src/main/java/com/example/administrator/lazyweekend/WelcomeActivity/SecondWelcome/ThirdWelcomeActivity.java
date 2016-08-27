package com.example.administrator.lazyweekend.WelcomeActivity.SecondWelcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.lazyweekend.MineFragment.InterestActivity;
import com.example.administrator.lazyweekend.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/27.
 */
public class ThirdWelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.welcome_third_left_btn)
    Button leftBtn;
    @BindView(R.id.welcome_third_right_btn)
    Button rightBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_third_activity_layout);
        ButterKnife.bind(this);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
      switch (viewId){
          case R.id.welcome_third_left_btn:
              Intent intent=new Intent(this,SecondWelcomeActivity.class);
              startActivity(intent);
              finish();
              break;
          case R.id.welcome_third_right_btn:
              Intent intent2=new Intent(this, InterestActivity.class);
              intent2.putExtra("name","xiejun");
              startActivity(intent2);
              finish();
              break;
      }
    }
}
