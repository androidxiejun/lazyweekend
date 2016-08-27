package com.example.administrator.lazyweekend.WelcomeActivity.SecondWelcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.lazyweekend.R;

/**
 * Created by Administrator on 2016/8/27.
 */
public class FirstWelcomeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_first_activity);
       mHandler.sendEmptyMessageDelayed(1,2000);
    }
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try {
                Thread.sleep(2000);
                Intent intent =new Intent(FirstWelcomeActivity.this,SecondWelcomeActivity.class);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
