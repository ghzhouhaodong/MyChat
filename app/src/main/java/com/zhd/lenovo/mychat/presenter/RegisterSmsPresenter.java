package com.zhd.lenovo.mychat.presenter;

import android.text.TextUtils;

import com.zhd.lenovo.mychat.base.BasePresenter;
import com.zhd.lenovo.mychat.model.RegisterSmsModelImpl;
import com.zhd.lenovo.mychat.mview.RegisterSmsView;
import com.zhd.lenovo.mychat.utils.PhoneCheckUtils;

/**
 * Created by lenovo on 2017/7/5.
 */

public class RegisterSmsPresenter extends BasePresenter<RegisterSmsView> {
  private RegisterSmsModelImpl registerSmsModel;
    public RegisterSmsPresenter(){
        registerSmsModel =new RegisterSmsModelImpl();

    }
    public void getVerificationCode(String phone){
      if(TextUtils.isEmpty(phone)){
          view.phoneError(1);
         return ;
      }
     if(!PhoneCheckUtils.isChinaPhoneLegal(phone)){
         view.phoneError(2);

         return;
     }
   registerSmsModel.getVerificationCode(phone);
     view.showTimer();

    }
   public void nextStep(String phone,String sms){
       if(TextUtils.isEmpty(phone)){
        view.phoneError(1);
      return;
       }
   if(!PhoneCheckUtils.isChinaPhoneLegal(phone)){
 view.phoneError(2);
       return;
   }

  view.toNextPage();

   }
}
