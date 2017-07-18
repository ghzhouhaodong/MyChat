package com.zhd.lenovo.mychat.mview;

import com.zhd.lenovo.mychat.bean.PhotoBean;

/**
 * Created by lenovo on 2017/7/11.
 */

public interface FirstFragmentView {
 public void success(PhotoBean photoBean,String page);
  public void failed(int code,String page);


}
