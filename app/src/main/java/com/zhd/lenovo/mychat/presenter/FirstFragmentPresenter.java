package com.zhd.lenovo.mychat.presenter;

import com.zhd.lenovo.mychat.base.BasePresenter;
import com.zhd.lenovo.mychat.bean.PhotoBean;
import com.zhd.lenovo.mychat.model.FirstFragmentModel;
import com.zhd.lenovo.mychat.model.FirstFramentModleImpl;
import com.zhd.lenovo.mychat.mview.FirstFragmentView;

/**
 * Created by lenovo on 2017/7/11.
 */

public class FirstFragmentPresenter extends BasePresenter<FirstFragmentView>{
  private FirstFragmentModel firstFragmentModel;
   public FirstFragmentPresenter(){
       firstFragmentModel =new FirstFramentModleImpl();
   }

 public void getData(String page){
   firstFragmentModel.getData(page, new FirstFragmentModel.DataListener() {
       @Override
       public void onSuccess(PhotoBean photoBean, String page) {
      view.success(photoBean,page);

       }

       @Override
       public void onFailed(int code, String page) {
           view.failed(code,page);


       }
   });

 }




}
