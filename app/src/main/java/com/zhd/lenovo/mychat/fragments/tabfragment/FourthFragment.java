package com.zhd.lenovo.mychat.fragments.tabfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.activirys.SWCameraStreamingActivity;
import com.zhd.lenovo.mychat.activirys.ShowListActivity;
import com.zhd.lenovo.mychat.activirys.UploadPhotoAlbumActivity;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.zhubobean;
import com.zhd.lenovo.mychat.network.BaseObserver;
import com.zhd.lenovo.mychat.network.RetrofitManager;
import com.zhd.lenovo.mychat.network.cookie.CookiesManager;
import com.zhd.lenovo.mychat.utils.Constants;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;
import com.zhd.lenovo.mychat.widget.MyToast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourthFragment extends Fragment {


    @BindView(R.id.btn_save_photo)
    Button btnSavePhoto;
    Unbinder unbinder;
    @BindView(R.id.quit_login)
    Button quitLogin;
    @BindView(R.id.myname_frag_four)
    TextView myname_txt;
    @BindView(R.id.Fourth_show)
    Button FourthShow;
    @BindView(R.id.Fourth_watch)
    Button FourthWatch;

    public FourthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);


        unbinder = ButterKnife.bind(this, view);
        String myname = PreferencesUtils.getValueByKey(getActivity(), "myname", "未登录");
        myname_txt.setText(myname);
        boolean islogin = PreferencesUtils.getValueByKey(getActivity(), "islogin", false);
        if (islogin) {
            quitLogin.setVisibility(view.VISIBLE);

        } else {
            quitLogin.setVisibility(view.GONE);

        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.btn_save_photo, R.id.quit_login,R.id.Fourth_show, R.id.Fourth_watch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save_photo:
                startActivity(new Intent(getActivity(), UploadPhotoAlbumActivity.class));

                break;
            case R.id.quit_login:


                boolean b = new CookiesManager(IApplication.application).removeAllCookie();
                if (b) {
                PreferencesUtils.addConfigInfo(getActivity(), "islogin", false);
                MyToast.makeText(IApplication.getApplication(), "退出登录", Toast.LENGTH_SHORT);

            }


                break;
            case R.id.Fourth_show:

                Map<String,String>map = new HashMap<String, String>();
              //  ?user.sign=1&live.type=1
                map.put("live.type","1");
                RetrofitManager.post(Constants.ZHIBO, map, new BaseObserver() {
                    @Override
                    public void onSuccess(String result) throws IOException {
                        System.out.println("zhiboresult = " + result);
                        Gson gson=new Gson();
                       zhubobean zhubo=   gson.fromJson(result, zhubobean.class);
                        Intent intent = new Intent(getActivity(),SWCameraStreamingActivity.class);
                       intent.putExtra("stream_json_str",zhubo.getUrl());
                       startActivity(intent);


                    }

                    @Override
                    public void onFailed(int code) {

                    }
                });




                break;
            case R.id.Fourth_watch:
      startActivity(new Intent(getActivity(), ShowListActivity.class));

             /*   publishUrl : rtmp://pili-publish.2dyt.com/1503d/m15jQIaW?e=1502118726&token=tYBGEzG7NE_D23EScw43ZTxynVkyt1IpHig5WHRY:B1wRfRiDKPmf-hD8eGE_sn7kR_8=
         * playUrl : rtmp://pili-live-rtmp.2dyt.com/1503d/m15jQIaW*/
                break;


        }
    }



}
