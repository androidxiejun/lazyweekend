package com.example.administrator.lazyweekend.MainFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidxx.yangjw.httplibrary.HttpUtils;
import com.androidxx.yangjw.httplibrary.ICallback;
import com.androidxx.yangjw.imageloader.ImageLoader;
import com.example.administrator.lazyweekend.LoginActivity.LoginActivity;
import com.example.administrator.lazyweekend.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MainFragment extends Fragment implements ICallback{
    private Context context;
    private int pageNum=1;
    private MyAdapter myAdapter;
    private PullToRefreshListView refreshListView;
    private List<MainInfo>infoList=new ArrayList<>();
    protected boolean isCreated = false;
    private ProgressDialog mPd;
    private ProgressBar mPb;
    private  String url_path="http://api.lanrenzhoumo.com/main/recommend/index?v=3&session_id=000040a3fb7d64ce1737c6c7bb3c7e4e157c91&lon=114.30963859310197&page=";
   private String url_path2="&lat=30.575388756810078";
    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        getJson();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPd=new ProgressDialog(context);
        mPb=new ProgressBar(context);
        View view=inflater.inflate(R.layout.main_list_view_layout,container,false);
        refreshListView= (PullToRefreshListView) view.findViewById(R.id.main_list_view_layout);
        myAdapter=new MyAdapter();
        refreshListView.setAdapter(myAdapter);
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
               pageNum=1;
                getJson();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                 pageNum+=1;
                 getJson();
            }
        });
        return view;
    }
    private void getJson(){
        HttpUtils.load(url_path+pageNum+url_path2).callback(this,1);
    }

    @Override
    public void success(String result, int requestCode) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                MainInfo info=new MainInfo();
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
                info.distance=jsonObject1.getInt("distance");
                infoList.add(info);
            }
            myAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class MyAdapter extends BaseAdapter{

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
                view=LayoutInflater.from(context).inflate(R.layout.main_list_view_item,viewGroup,false);
                viewHolder=new ViewHolder(view);
            }else{
                viewHolder= (ViewHolder) view.getTag();
            }
            MainInfo info=infoList.get(i);
            viewHolder.img.setImageResource(R.mipmap.ic_launcher);
            viewHolder.title.setText(info.title);
            viewHolder.endTime.setText(info.endTime+"");
            viewHolder.price.setText("$"+info.price);
            int number=info.distance/100;
            viewHolder.content.setText(info.content+"-"+(float)number/10+"km-"+info.category);
            viewHolder.collector.setText(info.collectNum+"人收藏");
            ImageLoader.init(context).load(info.url,viewHolder.img,mPb);
            viewHolder.collector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }
        class ViewHolder {
            public ImageView img;
            public TextView title,content,endTime,price;
            public Button collector;
            public ViewHolder(View view){
                view.setTag(this);
                this.img= (ImageView) view.findViewById(R.id.main_list_view_img);
                this.title= (TextView) view.findViewById(R.id.main_list_view_title);
                this.collector= (Button) view.findViewById(R.id.main_list_view_collect);
                this.content= (TextView) view.findViewById(R.id.main_list_view_content);
                this.endTime= (TextView) view.findViewById(R.id.main_list_view_endtime);
                this.price= (TextView) view.findViewById(R.id.main_list_view_money);
            }
        }
    }
}
