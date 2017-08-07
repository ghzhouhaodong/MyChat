package com.zhd.lenovo.mychat.fragments.tabfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.adapters.NineGridTest2Adapter;
import com.zhd.lenovo.mychat.base.BaseFragment;
import com.zhd.lenovo.mychat.bean.FriendsQuanBean;
import com.zhd.lenovo.mychat.model.NineGridTestModel;
import com.zhd.lenovo.mychat.mview.ThiredFragmentView;
import com.zhd.lenovo.mychat.presenter.ThiredFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThiredFragment extends BaseFragment<ThiredFragmentView,ThiredFragmentPresenter> implements ThiredFragmentView{


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NineGridTest2Adapter mAdapter;
  ThiredFragmentPresenter thiredFragmentPresenter=new ThiredFragmentPresenter();
    private List<NineGridTestModel> mList;
    private View view;
    private SpringView springView;
    private List<FriendsQuanBean.ListBean> listAll =new ArrayList<>();


    @Override
    public ThiredFragmentPresenter initPresenter() {


        return thiredFragmentPresenter;
    }

    public ThiredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_thired, container, false);
        springView = (SpringView) view.findViewById(R.id.thired_frag_springview);
        //DefaultHeader/Footer是SpringView已经实现的默认头/尾之一
        //更多还有MeituanHeader、AliHeader、RotationHeader等如上图7种
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        thiredFragmentPresenter.getData(System.currentTimeMillis() +"");
        mList = new ArrayList<>();
      //  initListData();
       // getIntentData();




      springView.setListener(new SpringView.OnFreshListener() {
          @Override
          public void onRefresh() {
              mAdapter.notifyDataSetChanged();
          }

          @Override
          public void onLoadmore() {

          }
      });
        return view;

    }


    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NineGridTest2Adapter(getActivity());
        mAdapter.setList(listAll);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void Success(FriendsQuanBean quanBean) {
        List<FriendsQuanBean.ListBean> list = quanBean.getList();
           listAll.addAll(list);
            initView();
    }

    @Override
    public void Failed(int code) {

    }
}
