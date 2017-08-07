package com.zhd.lenovo.mychat.model;

import com.zhd.lenovo.mychat.bean.FriendsQuanBean;

/**
 * Created by lenovo on 2017/8/7.
 */

public interface ThiredFragmentModel {

public void getData(String currenttimer,ThiredFragmentModelLisenter lisenter);






    public interface ThiredFragmentModelLisenter{
        public void onSuccess(FriendsQuanBean quanBean);
        public void onFailed(int code);
    }
}
