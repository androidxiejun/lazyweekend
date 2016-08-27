package com.example.d38_homework;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView mLv;
    private Button mBtn;
    private Context context;
    private List<String>productList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            productList.add("PRODUCT"+i);
        }
    }

    private void initView() {
        mLv= (ListView) findViewById(R.id.main_list_view);
        mBtn= (Button) findViewById(R.id.main_btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return productList==null?0:productList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View containView, ViewGroup viewGroup) {
            View view=containView;
            ViewHolder viewHolder=null;
            if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.recycle_view_item,viewGroup,false);
            viewHolder=new ViewHolder(view);
            }else{
                viewHolder= (ViewHolder) view.getTag();
            }
            viewHolder.productTxt.setText(productList.get(i));
            return view;
        }
        class ViewHolder {
            public TextView productTxt;
            public ViewHolder(View view){
                view.setTag(this);
                this.productTxt= (TextView) view.findViewById(R.id.cycle_view_txt);
            }
        }
    }
}
