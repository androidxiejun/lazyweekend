package com.example.administrator.lazyweekend.SearchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.lazyweekend.CallbackInfo;
import com.example.administrator.lazyweekend.MainActivity;
import com.example.administrator.lazyweekend.R;

/**
 * Created by Administrator on 2016/8/25.
 */
public class ChooseCityActivity extends AppCompatActivity implements View.OnClickListener,CallbackInfo{
    private ChooseCityFragment fragment;
    private FragmentManager manager;
    private Button backBtn;
    private String cityName="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_city_layout);
        backBtn= (Button) findViewById(R.id.choose_city_back_btn);
        backBtn.setOnClickListener(this);
        initFragment();
    }

    private void initFragment() {
        fragment=ChooseCityFragment.newInstance();
        manager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.choose_city_frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("cityName",cityName);
        startActivity(intent);
        finish();
    }

    @Override
    public void sendStr(String cityName) {
        this.cityName=cityName;
        Toast.makeText(ChooseCityActivity.this, cityName, Toast.LENGTH_LONG).show();
    }
}
