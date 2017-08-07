package com.zhd.lenovo.mychat.network;

import com.socks.library.KLog;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 2017/7/7
 */

public abstract  class BaseObserver implements Observer<String> {


    @Override
    public void onSubscribe(@NonNull Disposable d) {


    }



    @Override
    public void onNext(@NonNull String s) {


        KLog.i(s);

        try {
            onSuccess(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace() ;
        System.out.println();
        KLog.i(e);

        onFailed(0);

    }

    @Override
    public void onComplete() {

    }


    public abstract void onSuccess(String result) throws IOException;
    public abstract void onFailed(int code);

}
