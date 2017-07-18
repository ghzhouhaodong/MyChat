package com.zhd.lenovo.mychat.fragments.tabfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.activirys.ChatActivity;
import com.zhd.lenovo.mychat.adapters.SecondFragAdapter;
import com.zhd.lenovo.mychat.base.BaseFragment;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.FriendBean;
import com.zhd.lenovo.mychat.bean.FriendDataBean;
import com.zhd.lenovo.mychat.daoutils.FriendDataDaoUtils;
import com.zhd.lenovo.mychat.mview.SecondFragmentView;
import com.zhd.lenovo.mychat.presenter.SecondFramgentPresenter;
import com.zhd.lenovo.mychat.widget.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends BaseFragment<SecondFragmentView, SecondFramgentPresenter> implements SecondFragmentView {

    SecondFramgentPresenter secondFramgentPresenter = new SecondFramgentPresenter();
   boolean flag=true;
    Unbinder unbinder;
    @BindView(R.id.listview_pull)
    PullToRefreshListView listviewPull;
    private List<FriendDataBean>queryalllist=new ArrayList<FriendDataBean>();
    private List<FriendDataBean> query;
    private SecondFragAdapter secondFragAdapter;

    @Override
    public SecondFramgentPresenter initPresenter() {
        return secondFramgentPresenter;
    }

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        unbinder = ButterKnife.bind(this, view);

        listviewPull.setMode(PullToRefreshBase.Mode.BOTH);//两端刷新
//上拉文字设置
        ILoadingLayout upText = listviewPull.getLoadingLayoutProxy(false, true);
        upText.setPullLabel("上拉加载");
        upText.setRefreshingLabel("加载中");
        upText.setReleaseLabel("释放加载");
//下拉文字设置
        ILoadingLayout downText= listviewPull.getLoadingLayoutProxy(true, false);
        downText.setPullLabel("下拉刷新");
        downText.setRefreshingLabel("正在刷新...");
        downText.setReleaseLabel("松开刷新");

          listviewPull.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  int userId = queryalllist.get(position-1).getUserId();
                  Intent intent=new Intent(getActivity(),ChatActivity.class);
          intent.putExtra("userid",userId+"");
            startActivity(intent);



              }
          });


        listviewPull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //处理刷新操作
           //  Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_SHORT).show();
                   flag=true;
                  secondFramgentPresenter.getData(System.currentTimeMillis() +"");



            }
            @Override    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //处理加载操作
             //   Toast.makeText(MainActivity.this, "上拉加载", Toast.LENGTH_SHORT).show();
                if(queryalllist.size()>0){

                       Toast.makeText(getActivity(),  queryalllist.size()+"",Toast.LENGTH_SHORT).show();
                    long lasttime = queryalllist.get(queryalllist.size()-1).getLasttime();
                    secondFramgentPresenter.getData(lasttime+"");
                    MyToast.makeText(IApplication.application,lasttime+"",Toast.LENGTH_SHORT);

                }

                listviewPull.onRefreshComplete();

            }});

        secondFramgentPresenter.getData(System.currentTimeMillis() +"");

        return view;


    }






    @Override
    public void Success(FriendBean friendBean) {
        List<FriendDataBean> data = friendBean.getData();

    if(flag) {
         if(data!=null&&data.size()>0){


         FriendDataDaoUtils.delete();
         FriendDataDaoUtils.insertOrReplace(data);
        queryalllist.clear();
        //  data.clear();
        query = FriendDataDaoUtils.query();
        queryalllist.addAll(query);
        query.clear();
           if(queryalllist.size()>0){


               secondFragAdapter = new SecondFragAdapter(getActivity(), queryalllist);
               listviewPull.setAdapter(secondFragAdapter);
               listviewPull.onRefreshComplete();
               flag=false;
           }

         }
    }else{
        if(queryalllist.size()>0){

         //   queryalllist.addAll(data);
       //     secondFragAdapter.notifyDataSetChanged();
       //     data.clear();
        }


    }

    }

    @Override
    public void Failed(int code) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




}
