package com.zhd.lenovo.mychat.model;

import cn.smssdk.SMSSDK;

/**
 * Created by lenovo on 2017/7/5.
 */

public class RegisterSmsModelImpl implements RegisterSmsModel {
   public void getVerificationCode(String phone){
      SMSSDK.getVerificationCode("86",phone);


   }


}
