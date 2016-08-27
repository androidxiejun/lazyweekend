package com.example.administrator.lazyweekend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2016/8/25.
 */
public class RegistActivity extends AppCompatActivity implements View.OnClickListener{
    private Button backBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);
        backBtn= (Button) findViewById(R.id.regist_back_btn);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
           finish();
    }
}
