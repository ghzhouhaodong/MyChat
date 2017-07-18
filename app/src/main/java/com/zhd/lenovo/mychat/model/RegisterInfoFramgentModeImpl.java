package com.zhd.lenovo.mychat.model;

import com.google.gson.Gson;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.RegisterBean;
import com.zhd.lenovo.mychat.core.SortUtils;
import com.zhd.lenovo.mychat.network.BaseObserver;
import com.zhd.lenovo.mychat.network.RetrofitManager;
import com.zhd.lenovo.mychat.utils.Constants;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/6.
 */

public class RegisterInfoFramgentModeImpl implements RegisterinfoFramgent{


    @Override
    public void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, final RegisterInforFragmentDataListener listener) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("user.phone",phone);
        map.put("user.nickname",nickname);
        map.put("user.password",password);
        map.put("user.gender",sex);
        map.put("user.area",area);
        map.put("user.age",age);
        map.put("user.introduce",introduce);
        String lat = PreferencesUtils.getValueByKey(IApplication.getApplication(), "lat", "0");
        String lng = PreferencesUtils.getValueByKey(IApplication.getApplication(), "lng", "0");

       if(lat.equals("0")||lng.equals("0")){

       }else{
           map.put("user.lat",lat);
           map.put("user.lng",lng);
       }


        System.out.println("SortUtils.getMapResult(SortUtils.sortString(map)) = " + SortUtils.getMapResult(SortUtils.sortString(map)));

     //在RetrofitManager中 post 请求封装里MD5 验签消息 将他添加到map集合中
      //  System.out.println("sign = " + sign);
//http://qhb.2dyt.com/MyInterface/userAction_add.action  "http:169.254.69.102:8080/MyInterface/userAction_add.action"
        RetrofitManager.post(Constants.REGISTER_ACTION, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);
                if(registerBean.getResult_code() == 200){
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"phone",registerBean.getData().getPhone());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"password",registerBean.getData().getPassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"yxpassword",registerBean.getData().getYxpassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"uid",registerBean.getData().getUserId()+"");
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"nickname",registerBean.getData().getNickname());

                }
                listener.onSuccess(registerBean);
            }
            @Override
            public void onFailed(int code) {

                listener.onFailed(code);

            }
        });

    }




}
