package com.zhd.lenovo.mychat.fragments.tabfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.adapters.FirstFragAdapter;
import com.zhd.lenovo.mychat.base.BaseFragment;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.DataBean;
import com.zhd.lenovo.mychat.bean.PhotoBean;
import com.zhd.lenovo.mychat.daoutils.FirstFragmentDaoUtils;
import com.zhd.lenovo.mychat.mview.FirstFragmentView;
import com.zhd.lenovo.mychat.presenter.FirstFragmentPresenter;
import com.zhd.lenovo.mychat.widget.MyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BaseFragment<FirstFragmentView, FirstFragmentPresenter> implements FirstFragmentView {
    @BindView(R.id.recyclerview_first)
    RecyclerView recyclerviewFirst;
    Unbinder unbinder;
    @BindView(R.id.relat_backgroud_first)
    RelativeLayout relatBackgroudFirst;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private HorizontalDividerItemDecoration horizontalDividerItemDecoration;
    FirstFragAdapter adapter;
    @BindView(R.id.change_mode)
    TextView changeMode;
    FirstFragmentPresenter firstFragmentPresenter = new FirstFragmentPresenter();
    @BindView(R.id.springview_first)
    SpringView springviewFirst;
    int page = 1;
    private long lasttime1;
    private List<DataBean> data;
    Handler handler = new Handler();

    @Override
    public FirstFragmentPresenter initPresenter() {
        return firstFragmentPresenter;
    }

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        unbinder = ButterKnife.bind(this, view);
              relatBackgroudFirst.setBackgroundResource(R.color.backgroud);
        //加载本地的数据
        getLocationData();

        initData();

        return view;
    }

    private void initData() {
        changeMode.setTag(1);
        firstFragmentPresenter.getData(1 + "");
        horizontalDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity()).build();
        adapter = new FirstFragAdapter(getActivity());
        presenter.getData(page + "");
        toLinearLayoutManager();
        springviewFirst.setHeader(new DefaultHeader(getActivity()));
        springviewFirst.setFooter(new DefaultFooter(getActivity()));

        springviewFirst.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                page = 1;
                presenter.getData(page + "");

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (springviewFirst != null) {
                            springviewFirst.onFinishFreshAndLoad();
                        }

                    }
                }, 2000);


            }

            @Override
            public void onLoadmore() {


                presenter.getData(lasttime1 + "");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (springviewFirst != null) {
                            springviewFirst.onFinishFreshAndLoad();
                        }

                    }
                }, 2000);


            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }


    public void toLinearLayoutManager() {
        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        adapter.dataChange(1);
        recyclerviewFirst.setLayoutManager(linearLayoutManager);
        recyclerviewFirst.setAdapter(adapter);
        recyclerviewFirst.addItemDecoration(horizontalDividerItemDecoration);
        //本地数据设置到适配器
        adapter.setData(data);
    }

    public void toStaggeredGridLayoutManager() {
        if (staggeredGridLayoutManager == null) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
        adapter.dataChange(2);
        recyclerviewFirst.setLayoutManager(staggeredGridLayoutManager);
        recyclerviewFirst.setAdapter(adapter);
        recyclerviewFirst.removeItemDecoration(horizontalDividerItemDecoration);

    }


    @Override
    public void success(PhotoBean photoBean, String page) {
        FirstFragmentDaoUtils.delete();
        data = photoBean.getData();


        //当返回的数据大于0条时
        if (data != null && data.size() > 0) {

            int size = data.size();

            lasttime1 = data.get(size - 1).getLasttime();
        }


        MyToast.makeText(IApplication.getApplication(), photoBean.getPageCount() + "", Toast.LENGTH_SHORT);
        System.out.println("photoBean = " + photoBean.getPageCount());
        adapter.setData(photoBean, page + "");

    }

    @Override
    public void failed(int code, String page) {


    }

    @OnClick(R.id.change_mode)
    public void onClick(View v) {
        int tag = (int) v.getTag();
        if (tag == 1) {
            changeMode.setTag(2);
            toStaggeredGridLayoutManager();


        } else {
            changeMode.setTag(1);
            toLinearLayoutManager();
        }
    }

    public void getLocationData() {
        //加载本地的数据
        data = FirstFragmentDaoUtils.query();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler = null;
        }

    }

}
