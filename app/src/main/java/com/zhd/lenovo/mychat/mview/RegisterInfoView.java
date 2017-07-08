package com.zhd.lenovo.mychat.mview;

import com.zhd.lenovo.mychat.bean.RegisterBean;

/**
 * Created by lenovo on 2017/7/5.
 */

public interface RegisterInfoView {
     public void registerSuccess(RegisterBean registerBean);
    public void registerFailed(int code);

}
