package com.example.administrator.lazyweekend.SearchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidxx.yangjw.httplibrary.HttpUtils;
import com.androidxx.yangjw.httplibrary.ICallback;
import com.androidxx.yangjw.imageloader.ImageLoader;
import com.example.administrator.lazyweekend.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class SeachMsg extends AppCompatActivity implements ICallback,View.OnClickListener{
    private PullToRefreshListView mLv;
    private String title,name;
    private Context context;
    private TextView mTitle;
    private MyAdapter myAdapter;
    private List<SearchMsgInfo>infoList=new ArrayList<>();
    private Button backImg;
    private ProgressDialog mPd;
    private ProgressBar mPb;
    private RelativeLayout relativeLayout;
    private final String URL_PATH="http://api.lanrenzhoumo.com/wh/common/leos?v=2&session_id=000040a3fb7d64ce1737c6c7bb3c7e4e157c91&lon=114.30963859310197&page=1&category=";
    private final String URL_PATH2="&lat=30.575388756810078&city_id";
    private final String URL_PATH3="http://api.lanrenzhoumo.com/wh/common/leos?v=2&session_id=000040cf1c900b2ef3fb688420c865f87714d2&lon=114.428966&page=1&category=all&lat=30.419685&city_id=";
    private int cityId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_msg_layout);
        context=this;
        initDialog();
        initIntent();
        initView();
    }

    private void initDialog() {
        mPd=new ProgressDialog(context);
        mPd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mPd.setTitle("正在加载");
        mPd.show();
    }

    private void initIntent() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        name=intent.getStringExtra("name");
        cityId=intent.getIntExtra("cityId",0);
        if(cityId==0){
            cityId=192;
        HttpUtils.load(URL_PATH+name+URL_PATH2+cityId).callback(this,3);
        }else{
            HttpUtils.load(URL_PATH3+cityId).callback(this,4);
        }
    }

    private void initView() {
        mPb= new ProgressBar(context);
//        LinearLayout layout = new LinearLayout(context);
//        layout.addView(mPb, new ViewGroup.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT));
//        setContentView(layout);
        mLv= (PullToRefreshListView) findViewById(R.id.search_msg_fresh_list_view);
        backImg= (Button) findViewById(R.id.search_msg_back);
        backImg.setOnClickListener(this);
        myAdapter=new MyAdapter();
        mTitle= (TextView) findViewById(R.id.search_msg_title);
        mTitle.setText(title);
        mLv.setAdapter(myAdapter);

    }

    @Override
    public void success(String result, int requestCode) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                SearchMsgInfo info=new SearchMsgInfo();
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                JSONArray jsonArray1=jsonObject1.getJSONArray("front_cover_image_list");
                info.url=jsonArray1.get(0).toString();
                info.title=jsonObject1.getString("title");
                info.content=jsonObject1.getString("poi");
                info.collectNum=jsonObject1.getInt("collected_num");
                info.endTime=jsonObject1.getString("time_info");
                info.category=jsonObject1.getString("category");
                info.leo_id=jsonObject1.getInt("leo_id");
                info.price=jsonObject1.getInt("price");
                info.timeDesc=jsonObject1.getString("time_desc");
                infoList.add(info);
            }
            mPd.dismiss();
            myAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
//      finish();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infoList==null?0:infoList.size();
        }

        @Override
        public Object getItem(int i) {
            return infoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View containView, ViewGroup viewGroup) {
            View view=containView;
            ViewHolder viewHolder=null;
            if(view==null){
                view= LayoutInflater.from(context).inflate(R.layout.main_list_view_item,viewGroup,false);
                viewHolder=new ViewHolder(view);
            }else{
                viewHolder= (ViewHolder) view.getTag();
            }
            SearchMsgInfo info=infoList.get(i);
//            viewHolder.img.setImageResource(R.mipmap.ic_launcher);
//            mPd.show();

            mPb.setVisibility(ProgressBar.VISIBLE);
            viewHolder.title.setText(info.title);
            viewHolder.endTime.setText(info.endTime+"");
            viewHolder.price.setText("$"+info.price);
            viewHolder.content.setText(info.content+"."+info.category);
            viewHolder.collector.setText(info.collectNum+"人收藏");
            ImageLoader.init(context).load(info.url,viewHolder.img,mPb);
            return view;
        }
        class ViewHolder {
            public ImageView img;
            public TextView title,content,endTime,collector,price;
            public ViewHolder(View view){
                view.setTag(this);
                this.img= (ImageView) view.findViewById(R.id.main_list_view_img);
                this.title= (TextView) view.findViewById(R.id.main_list_view_title);
                this.collector= (TextView) view.findViewById(R.id.main_list_view_collect);
                this.content= (TextView) view.findViewById(R.id.main_list_view_content);
                this.endTime= (TextView) view.findViewById(R.id.main_list_view_endtime);
                this.price= (TextView) view.findViewById(R.id.main_list_view_money);
            }
        }
    }
}
