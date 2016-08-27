package com.example.d19_homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/31.
 */
public class MyCategeryFragment extends Fragment {
    static MyCategeryFragment newInstance(){
        return new MyCategeryFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.main_category_fragment_view,container,false);
        return view;
    }
}
