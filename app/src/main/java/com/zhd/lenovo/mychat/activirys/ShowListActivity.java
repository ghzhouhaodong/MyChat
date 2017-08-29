package com.zhd.lenovo.mychat.activirys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.adapters.ShowListAdapter;
import com.zhd.lenovo.mychat.base.IActivity;
import com.zhd.lenovo.mychat.bean.DisplayBean;
import com.zhd.lenovo.mychat.network.BaseObserver;
import com.zhd.lenovo.mychat.network.RetrofitManager;
import com.zhd.lenovo.mychat.utils.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowListActivity extends IActivity implements ShowListAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private List<DisplayBean.ListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_show_list);
        if (staggeredGridLayoutManager == null) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
  recyclerView.setLayoutManager(staggeredGridLayoutManager);
        Map<String,String> map = new HashMap<String, String>();
        //  ?user.sign=1&live.type=1
        map.put("live.type","2");
        RetrofitManager.post(Constants.ZHIBO, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) throws IOException {
                Gson gson=new Gson();
                DisplayBean displayBean = gson.fromJson(result, DisplayBean.class);

                list = displayBean.getList();
                ShowListAdapter listAdapter= new ShowListAdapter(ShowListActivity.this, list);
               listAdapter.setOnItemClickListener(ShowListActivity.this);
                recyclerView.setAdapter(listAdapter);

            }

            @Override
            public void onFailed(int code) {

            }
        });


    }


    @Override
    public void onItemClickListener(int position, View view) {

        Intent intent2 = new Intent(this,PLVideoViewActivity.class);
        intent2.putExtra("videoPath",list.get(position).getPlayUrl());
        intent2.putExtra("zbshowid",list.get(position).getId());

        startActivity(intent2);




    }

    @Override
    public void onItemLongClickListener(int position, View view) {




    }
}
