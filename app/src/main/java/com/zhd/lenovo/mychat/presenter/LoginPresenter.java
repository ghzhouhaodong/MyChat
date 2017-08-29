package com.zhd.lenovo.mychat.presenter;

import android.text.TextUtils;

import com.zhd.lenovo.mychat.base.BasePresenter;
import com.zhd.lenovo.mychat.model.LoginActivityModelImpl;
import com.zhd.lenovo.mychat.mview.LoginView;

/**
 * Created by lenovo on 2017/7/4.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
 public LoginView loginView;
public LoginActivityModelImpl loginActivityModel;
 public LoginPresenter(LoginView loginView){
     this.loginView =loginView;
     this.loginActivityModel=new LoginActivityModelImpl();

 }
   public void setLoginView(String username,String password){
      if(TextUtils.isEmpty(username)){
           loginView.usernameEmpty();
           return;
       }
     if(TextUtils.isEmpty(password)){
         loginView.passwordEmpty();
         return;

     }
    loginActivityModel.Login(username, password, new LoginActivityModelImpl.LoginActivityModelImplListener() {
        @Override
        public void Success(String result,String myname,String myid) {
          loginView.LoginSuccess(result,myname, myid);
        }

        @Override
        public void Failed() {
          loginView.LoginFailed();
        }
    });

   }



}
