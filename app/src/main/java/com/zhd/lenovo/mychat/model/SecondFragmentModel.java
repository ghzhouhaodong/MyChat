package com.zhd.lenovo.mychat.model;

import com.zhd.lenovo.mychat.bean.FriendBean;

/**
 * Created by lenovo on 2017/7/15.
 */

public interface SecondFragmentModel {
 public void getData(String currenttimer,SecondFragmentModelLisenter lisenter);

    public interface SecondFragmentModelLisenter{
        public void onSuccess(FriendBean friendBean);
        public void onFailed(int code);
    }



}
