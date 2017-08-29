package com.zhd.lenovo.mychat.model;

import com.zhd.lenovo.mychat.bean.RegisterBean;

/**
 * Created by lenovo on 2017/7/6.
 */

public interface RegisterinfoFramgent {

   public void getData(String phone,String nickname,String sex,String age,String area,String introduce,String password,RegisterInforFragmentDataListener listener);
       public interface RegisterInforFragmentDataListener{
          public void onSuccess(RegisterBean registerBean);

          public void onFailed(int code);

       }





}
