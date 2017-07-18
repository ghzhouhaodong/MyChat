package com.zhd.lenovo.mychat.model;

import com.zhd.lenovo.mychat.bean.ItemBean;
import com.zhd.lenovo.mychat.bean.PhotoBean;
import com.zhd.lenovo.mychat.daoutils.FirstFragmentDaoUtils;
import com.zhd.lenovo.mychat.network.BaseObserver;
import com.zhd.lenovo.mychat.network.RetrofitManager;
import com.zhd.lenovo.mychat.utils.Constants;
import com.zhd.lenovo.mychat.utils.GsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/11.
 */

public class FirstFramentModleImpl implements  FirstFragmentModel {


    @Override
    public void getData(final String page, final DataListener listener) {
        Map map = new HashMap<String,String>();
        //pageIndex=1&pageSize=10&user.sign=1
      if(page.equals("1")){
          map.put("user.currenttimer", System.currentTimeMillis() +"");

      }else{
            map.put("user.currenttimer", page+"");


        }


        // String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
 //map.put("user.sign",sign);
        RetrofitManager.post(Constants.PHOTO_ACTION, map, new BaseObserver() {

            @Override
            public void onSuccess(String result) {
                GsonUtil instance = GsonUtil.getInstance();
                PhotoBean photoBean = instance.fromJson(result, PhotoBean.class);
             listener.onSuccess(photoBean,page+"");
     //数据本地化
                FirstFragmentDaoUtils.insert(photoBean.getData());

            }

            @Override
            public void onFailed(int code) {
            listener.onFailed(code,page);

            }
        });

    }
    @Override
 public void getItemData(int  userid , final ItemDataListener listener){
     Map map = new HashMap<String,String>();
     map.put("user.userId",userid+"");
     RetrofitManager.post(Constants.USER_INFO, map, new BaseObserver() {
         @Override
         public void onSuccess(String result) {
             System.out.println("result = " + result);
             GsonUtil instance = GsonUtil.getInstance();
             ItemBean itemBean = instance.fromJson(result, ItemBean.class);
             listener.onSuccess(itemBean);

         }

         @Override
         public void onFailed(int code) {
          listener.onFailed(code);
         }
     });


 }

}
