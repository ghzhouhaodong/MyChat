package com.zhd.lenovo.mychat.mview;

/**
 * Created by lenovo on 2017/7/5.
 */

public interface RegisterSmsView {
  public void phoneError(int type);
   //显示倒计时
  public void showTimer();
   //下一个页面
    public void toNextPage();

}
