package com.zhd.lenovo.mychat.model;

import com.zhd.lenovo.mychat.bean.FriendBean;
import com.zhd.lenovo.mychat.network.BaseObserver;
import com.zhd.lenovo.mychat.network.RetrofitManager;
import com.zhd.lenovo.mychat.utils.Constants;
import com.zhd.lenovo.mychat.utils.GsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/15.
 */

public class SecondFragmentModelImpl implements SecondFragmentModel{


    @Override
    public void getData(String currenttimer, final SecondFragmentModelLisenter lisenter) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user.currenttimer",currenttimer);
        System.out.println("map = " + map.get("user.currenttimer"));
        RetrofitManager.post(Constants.FRIEND_LINEAR, map, new BaseObserver() {

            @Override
            public void onSuccess(String result) {

                System.out.println("SecondFragment的数据 = " + result);
                GsonUtil instance = GsonUtil.getInstance();
                FriendBean friendBean = instance.fromJson(result, FriendBean.class);

                lisenter.onSuccess(friendBean);

            }

            @Override
            public void onFailed(int code) {
             lisenter.onFailed(code);

            }
        });

    }
}
