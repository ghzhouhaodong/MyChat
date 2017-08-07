package com.zhd.lenovo.mychat.model;

import com.zhd.lenovo.mychat.bean.FriendsQuanBean;
import com.zhd.lenovo.mychat.network.BaseObserver;
import com.zhd.lenovo.mychat.network.RetrofitManager;
import com.zhd.lenovo.mychat.utils.Constants;
import com.zhd.lenovo.mychat.utils.GsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/8/7.
 */

public class ThiredFragmentModelImpl implements  ThiredFragmentModel{


    @Override
    public void getData(String currenttimer, final ThiredFragmentModelLisenter lisenter) {
        Map<String,String> map=new HashMap<String, String>();
   // ?user.sign=1&dynamicinfor.dynamicTime=11111
       map.put("dynamicinfor.dynamicTime","11111");

        RetrofitManager.post(Constants.FRIEND_QUANG, map, new BaseObserver() {
            @Override
            public void onSuccess(String result){
                GsonUtil instance = GsonUtil.getInstance();
                FriendsQuanBean quanBean = instance.fromJson(result, FriendsQuanBean.class);
            lisenter.onSuccess(quanBean);

            }

            @Override
            public void onFailed(int code) {

            }
        });


    }




}
