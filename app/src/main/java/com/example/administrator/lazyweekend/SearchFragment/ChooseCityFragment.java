package com.example.administrator.lazyweekend.SearchFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;

import com.androidxx.yangjw.httplibrary.HttpUtils;
import com.androidxx.yangjw.httplibrary.ICallback;
import com.example.administrator.lazyweekend.CallbackInfo;
import com.example.administrator.lazyweekend.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/25.
 */
public class ChooseCityFragment extends Fragment implements ICallback{
    private Context context;
    private ExpandableListView mElv;
    private GridView mGv;
    private MyGridAdapter myGridAdapter;
    private MyExpandableAdapter myExpandableAdapter;
    private Map<String,List<String>>dataMap=new HashMap<>();
    private Map<Integer,List<Integer>>expIdMap=new HashMap<>();
    private List<String>typeList=new ArrayList<>();
    private List<String>cityList;
    private List<Integer>cityIdList;
    private CallbackInfo callback;
    private List<Integer>gridCityId=new ArrayList<>();
    private List<Integer>expCityId=new ArrayList<>();
    private List<String>hotCityList=new ArrayList<>();
    private final String URL_PATH="http://api.lanrenzhoumo.com/district/list/allcity?session_id=000040a3fb7d64ce1737c6c7bb3c7e4e157c91";
    public static ChooseCityFragment newInstance(){
        return new ChooseCityFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CallbackInfo){
            callback= (CallbackInfo) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HttpUtils.load(URL_PATH).callback(this,1);
        View view=inflater.inflate(R.layout.choose_city_head_item,null);
        View view2=inflater.inflate(R.layout.choose_city_food_item,container,false);
        mGv= (GridView) view.findViewById(R.id.choose_city_grid_view);
        myGridAdapter=new MyGridAdapter();
        mGv.setAdapter(myGridAdapter);
        myExpandableAdapter=new MyExpandableAdapter();
        mElv= (ExpandableListView) view2.findViewById(R.id.choose_city_expand_view);
        mElv.addHeaderView(view);
        mElv.setAdapter(myExpandableAdapter);
        return view2;
    }

    @Override
    public void success(String result, int requestCode) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONArray("result");
            JSONObject jsonObject1=jsonArray.getJSONObject(0);
            JSONArray jsonArray1=jsonObject1.getJSONArray("city_list");
            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsonObject2=jsonArray1.getJSONObject(i);
                String cityName=jsonObject2.getString("city_name");
                int cityId=jsonObject2.getInt("city_id");
                hotCityList.add(cityName);
                gridCityId.add(cityId);
            }
            for (int i = 1; i < jsonArray.length(); i++) {
                JSONObject jsonObject3=jsonArray.getJSONObject(i);
                String cityType=jsonObject3.getString("begin_key");
                typeList.add(cityType);
                JSONArray jsonArrayCity=jsonObject3.getJSONArray("city_list");
                cityList=new ArrayList<>();
                cityIdList=new ArrayList<>();
                for (int j = 0; j < jsonArrayCity.length(); j++) {
                    JSONObject jsonObjectCity=jsonArrayCity.getJSONObject(j);
                    String cityName=jsonObjectCity.getString("city_name");
                    int cityId=jsonObjectCity.getInt("city_id");
                    cityIdList.add(cityId);
                    cityList.add(cityName);
                }
                dataMap.put(cityType,cityList);
                expIdMap.put(i,cityIdList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myGridAdapter.notifyDataSetChanged();
        myExpandableAdapter.notifyDataSetChanged();
        for (int i = 0; i < typeList.size(); i++) {
           mElv.expandGroup(i);
        }
        mElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }

    class MyGridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return hotCityList==null?0:hotCityList.size();
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
                view=LayoutInflater.from(context).inflate(R.layout.choose_city_grid_view_item,null);
                viewHolder=new ViewHolder(view);
            }else{
                viewHolder= (ViewHolder) view.getTag();
            }
            final String cityName=hotCityList.get(i);
            final int cityId = gridCityId.get(i);
            viewHolder.cityBtn.setText(cityName);
            viewHolder.cityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    callback.sendStr(cityName);
                    Intent intent=new Intent(context,SeachMsg.class);
                    intent.putExtra("title",cityName);
                    intent.putExtra("name","all");
                    intent.putExtra("cityId",cityId);
                    startActivity(intent);
                }
            });
            return view;
        }
        class ViewHolder {
            public Button cityBtn;
            public ViewHolder(View view){
                view.setTag(this);
                this.cityBtn= (Button) view.findViewById(R.id.choose_city_grid_view_btn);
            }
        }
    }
   class MyExpandableAdapter extends BaseExpandableListAdapter{

       @Override
       public int getChildType(int groupPosition, int childPosition) {

           return super.getChildType(groupPosition, childPosition);
       }

       @Override
       public int getGroupCount() {
           return typeList==null?0:typeList.size();
       }

       @Override
       public int getChildrenCount(int i) {
           String type=typeList.get(i);
           List<String>child=dataMap.get(type);
           return child==null?0:child.size();
       }

       @Override
       public Object getGroup(int i) {
           return i;
       }

       @Override
       public Object getChild(int i, int i1) {
           return i1;
       }

       @Override
       public long getGroupId(int i) {
           return i;
       }

       @Override
       public long getChildId(int i, int i1) {
           return i1;
       }

       @Override
       public boolean hasStableIds() {
           return false;
       }

       @Override
       public View getGroupView(int i, boolean b, View containView, ViewGroup viewGroup) {
          View view=containView;
          String type=typeList.get(i);
           if(view==null){
               view=LayoutInflater.from(context).inflate(R.layout.choose_city_expandable_item,viewGroup,false);
           }
           TextView tv= (TextView) view.findViewById(R.id.choose_city_expananle_txt);
           tv.setText(type);
           return view;
       }

       @Override
       public View getChildView(int i, int i1, boolean b, View containView, ViewGroup viewGroup) {
           String type=typeList.get(i);
           List<String>list=dataMap.get(type);
           List<Integer>idList=expIdMap.get(i+1);
           View view=containView;
           ViewHolder viewHolder=null;
           if(view==null){
               view=LayoutInflater.from(context).inflate(R.layout.choose_city_expanable_list_view_item,viewGroup,false);
               viewHolder=new ViewHolder(view);
           }else{
               viewHolder= (ViewHolder) view.getTag();
           }
           final String cityName=list.get(i1);
           final int cityId=idList.get(i1);
            viewHolder.cityTxt.setText(cityName);
           viewHolder.cityTxt.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   callback.sendStr(cityName);
                   Intent intent=new Intent(context,SeachMsg.class);
                   intent.putExtra("title",cityName);
                   intent.putExtra("name","all");
                   intent.putExtra("cityId",cityId);
                   startActivity(intent);
               }
           });
           return view;
       }

       @Override
       public boolean isChildSelectable(int i, int i1) {
           return false;
       }
       class ViewHolder {
           public TextView cityTxt;
           public ViewHolder(View view){
               view.setTag(this);
               this.cityTxt= (TextView) view.findViewById(R.id.choose_city_expandable_list_view_txt);
           }

       }
   }
}
