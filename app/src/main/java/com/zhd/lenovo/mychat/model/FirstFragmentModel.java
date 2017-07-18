package com.zhd.lenovo.mychat.model;

import com.zhd.lenovo.mychat.bean.ItemBean;
import com.zhd.lenovo.mychat.bean.PhotoBean;

/**
 * Created by lenovo on 2017/7/11.
 */

public interface FirstFragmentModel {
 public void getData(String page,DataListener listener);
 public void getItemData(int userid,ItemDataListener listener);



public interface  DataListener{
   public void onSuccess(PhotoBean photoBean,String page);
   public void onFailed(int code,String page);

}
   public interface  ItemDataListener{
        public void onSuccess(ItemBean itemBean);
        public void onFailed(int code);

    }




}
