package com.example.administrator.lazyweekend.SearchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidxx.yangjw.httplibrary.HttpUtils;
import com.androidxx.yangjw.httplibrary.ICallback;
import com.androidxx.yangjw.imageloader.ImageLoader;
import com.example.administrator.lazyweekend.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class SearchFragment extends Fragment implements ICallback,View.OnClickListener{
    private Context context;
    private GridView mGv;
    private TextView searchTxt;
    private MyGridAdapter myGridAdapter;
    private List<SearchInfo>infoList=new ArrayList<>();
    private String cityNameTitle;
    private ProgressDialog mPd;
    private ProgressBar mPb;
    private final String URL_PATH="http://api.lanrenzhoumo.com/wh/common/cats?v=2&session_id=000040a3fb7d64ce1737c6c7bb3c7e4e157c91";
    public static SearchFragment newInstance(){
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        HttpUtils.load(URL_PATH).callback(this,2);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myGridAdapter=new MyGridAdapter();
        mPd=new ProgressDialog(context);
        mPb=new ProgressBar(context);
        View view=inflater.inflate(R.layout.search_layout,container,false);
        searchTxt= (TextView) view.findViewById(R.id.search_city_text);
//        searchTxt.setText(cityNameTitle);
        searchTxt.setOnClickListener(this);
        mGv= (GridView) view.findViewById(R.id.search_grid_view_layout);
        mGv.setAdapter(myGridAdapter);
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchInfo info=infoList.get(i);
                String title=info.title;
                String name=info.name;
                Intent intent=new Intent(context,SeachMsg.class);
                intent.putExtra("title",title);
                intent.putExtra("name",name);
                intent.putExtra("cityId",0);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void success(String result, int requestCode) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                SearchInfo info=new SearchInfo();
                info.imgUrl=jsonObject1.getString("icon_view");
                info.name=jsonObject1.getString("name");
                info.title=jsonObject1.getString("cn_name");
                infoList.add(info);
            }
           myGridAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
          Intent intent=new Intent(context,ChooseCityActivity.class);
           startActivity(intent);
    }

    class MyGridAdapter extends BaseAdapter{

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
          return 0;
      }

      @Override
      public View getView(int i, View containView, ViewGroup viewGroup) {
          View view=containView;
          ViewHolder viewHolder=null;
          if(view==null){
              view=LayoutInflater.from(context).inflate(R.layout.search_grid_view_item,viewGroup,false);
              viewHolder=new ViewHolder(view);
          }else{
              viewHolder= (ViewHolder) view.getTag();
          }
          SearchInfo info=infoList.get(i);
          viewHolder.title.setText(info.title);
          viewHolder.img.setImageResource(R.mipmap.ic_launcher);
          ImageLoader.init(context).load(info.imgUrl,viewHolder.img,mPb);
          return view;
      }
        class ViewHolder {
            public TextView title;
            public ImageView img;
            public ViewHolder(View view){
                view.setTag(this);
                this.img= (ImageView) view.findViewById(R.id.search_grad_view_img);
                this.title= (TextView) view.findViewById(R.id.search_grid_view_txt);
            }
        }
  }
    public void changeTxt(String cityName){
        cityNameTitle=cityName;
    }
}
