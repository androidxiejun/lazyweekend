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
public class MyGuideFragment extends Fragment {
    static MyGuideFragment newInstance(){
        return new MyGuideFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.main_guide_fragment_view,container,false);
        return view;
    }
}
