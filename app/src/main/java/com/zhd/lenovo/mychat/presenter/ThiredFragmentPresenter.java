package com.zhd.lenovo.mychat.presenter;

import com.zhd.lenovo.mychat.base.BasePresenter;
import com.zhd.lenovo.mychat.bean.FriendsQuanBean;
import com.zhd.lenovo.mychat.model.ThiredFragmentModel;
import com.zhd.lenovo.mychat.model.ThiredFragmentModelImpl;
import com.zhd.lenovo.mychat.mview.ThiredFragmentView;

/**
 * Created by lenovo on 2017/8/7.
 */

public class ThiredFragmentPresenter extends BasePresenter<ThiredFragmentView>{
    private ThiredFragmentModel thiredFragmentModel;
     public ThiredFragmentPresenter(){
  thiredFragmentModel =new ThiredFragmentModelImpl();


     }



    public void getData(String currenttimer){

        thiredFragmentModel.getData(currenttimer, new ThiredFragmentModel.ThiredFragmentModelLisenter() {
            @Override
            public void onSuccess(FriendsQuanBean quanBean) {
     view.Success(quanBean);


            }

            @Override
            public void onFailed(int code) {
  view.Failed(10);



            }
        });


    }





}
