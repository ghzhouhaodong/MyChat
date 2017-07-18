package com.zhd.lenovo.mychat.presenter;

import com.zhd.lenovo.mychat.base.BasePresenter;
import com.zhd.lenovo.mychat.bean.ItemBean;
import com.zhd.lenovo.mychat.model.FirstFragmentModel;
import com.zhd.lenovo.mychat.model.FirstFramentModleImpl;
import com.zhd.lenovo.mychat.mview.FirstFragmentItemView;

/**
 * Created by lenovo on 2017/7/13.
 */

public class FirstFragmentItemPresenter extends BasePresenter<FirstFragmentItemView>{
  private FirstFragmentModel firstFragmentModel;

    public FirstFragmentItemPresenter() {
        firstFragmentModel =new FirstFramentModleImpl();
    }

    public void getData(int userid){
   firstFragmentModel.getItemData(userid, new FirstFragmentModel.ItemDataListener() {
       @Override
       public void onSuccess(ItemBean itemBean) {
           view.success(itemBean);
       }

       @Override
       public void onFailed(int code) {
          //  view.failed(code);

       }
   });


    }



}
