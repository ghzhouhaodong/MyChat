package com.zhd.lenovo.mychat.mview;

import com.zhd.lenovo.mychat.bean.FriendBean;

/**
 * Created by lenovo on 2017/7/15.
 */

public interface SecondFragmentView {

      public void Success(FriendBean friendBean);
      public void Failed(int code);


}
