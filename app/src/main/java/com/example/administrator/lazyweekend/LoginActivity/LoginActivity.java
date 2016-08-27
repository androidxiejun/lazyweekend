package com.example.administrator.lazyweekend.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.lazyweekend.R;
import com.example.administrator.lazyweekend.RegistActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/23.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.login_back_btn)
    Button backBtn;
    @BindView(R.id.login_regist_txt)
    TextView registTxt;
    @BindView(R.id.login_weibo_btn)
    Button weiboBtn;
    @BindView(R.id.login_weixin_btn)
    Button weixinBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        setListener();
    }
    private void setListener(){
        backBtn.setOnClickListener(this);
        registTxt.setOnClickListener(this);
        weiboBtn.setOnClickListener(this);
        weixinBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        switch (viewId){
            case R.id.login_back_btn:
                finish();
                break;
            case R.id.login_regist_txt:
                Intent intent=new Intent(this, RegistActivity.class);
                startActivity(intent);
//                Toast.makeText(LoginActivity.this, ".........", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
