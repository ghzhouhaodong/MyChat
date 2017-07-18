package com.zhd.lenovo.mychat.presenter;

import com.zhd.lenovo.mychat.base.BasePresenter;
import com.zhd.lenovo.mychat.bean.FriendBean;
import com.zhd.lenovo.mychat.model.SecondFragmentModel;
import com.zhd.lenovo.mychat.model.SecondFragmentModelImpl;
import com.zhd.lenovo.mychat.mview.SecondFragmentView;

/**
 * Created by lenovo on 2017/7/15.
 */

public class SecondFramgentPresenter extends BasePresenter<SecondFragmentView>{
    private SecondFragmentModel secondFragmentModel;

    public SecondFramgentPresenter() {
      secondFragmentModel = new SecondFragmentModelImpl();

    }
   public void getData(String currenttimer){
     secondFragmentModel.getData(currenttimer, new SecondFragmentModel.SecondFragmentModelLisenter() {
         @Override
         public void onSuccess(FriendBean friendBean) {
       view.Success(friendBean);

         }

         @Override
         public void onFailed(int code) {
     view.Failed(code);

         }
     });

   }




}



