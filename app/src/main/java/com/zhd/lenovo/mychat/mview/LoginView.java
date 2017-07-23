package com.zhd.lenovo.mychat.mview;

/**
 * Created by lenovo on 2017/7/4.
 */

public interface LoginView {
 public void usernameEmpty();
 public void passwordEmpty();
  public void LoginSuccess(String result,String myname,String myid);
   public void LoginFailed();




}
