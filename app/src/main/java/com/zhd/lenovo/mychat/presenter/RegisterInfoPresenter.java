package com.zhd.lenovo.mychat.presenter;

import com.zhd.lenovo.mychat.base.BasePresenter;
import com.zhd.lenovo.mychat.bean.RegisterBean;
import com.zhd.lenovo.mychat.model.RegisterInfoFramgentModeImpl;
import com.zhd.lenovo.mychat.model.RegisterinfoFramgent;
import com.zhd.lenovo.mychat.mview.RegisterInfoView;

/**
 * Created by lenovo on 2017/7/5.
 */

  public class RegisterInfoPresenter extends BasePresenter<RegisterInfoView> {
    private RegisterinfoFramgent registerInfoFragmentmode;

     public RegisterInfoPresenter(){

         registerInfoFragmentmode =new RegisterInfoFramgentModeImpl();
     }
    public void vaildInfor(String phone,String nickname,String sex,String age,String area,String introduce,String password){


        //非空判断

        registerInfoFragmentmode.getData(phone, nickname, sex, age, area, introduce, password, new RegisterinfoFramgent.RegisterInforFragmentDataListener() {
            @Override
            public void onSuccess(RegisterBean registerBean) {
                view.registerSuccess(registerBean);
            }

            @Override
            public void onFailed(int code) {
                view.registerFailed(code);
            }
        });
    }

}
