package com.zhd.lenovo.mychat.daoutils;



import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.FriendDataBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by muhanxi on 17/7/10.
 */

public class FriendDataDaoUtils {


    public static void insertOrReplace(final List<FriendDataBean> list){
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {


                IApplication.daoSession.getFriendDataBeanDao().insertOrReplaceInTx(list);


            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                    }
                });
    }

     public static List<FriendDataBean>  query(){
         List<FriendDataBean> list = IApplication.daoSession.getFriendDataBeanDao().queryBuilder().list();

      return  list;
     }

   public static  void  delete(){

       IApplication.daoSession.getFriendDataBeanDao().deleteAll();



   }

}
