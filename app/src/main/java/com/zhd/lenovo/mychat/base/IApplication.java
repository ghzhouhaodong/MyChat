package com.zhd.lenovo.mychat.base;

import com.mob.MobApplication;



public class IApplication extends MobApplication {


    public static IApplication application ;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;





    }



    public static IApplication getApplication(){
        if(application == null){
            application = getApplication() ;
        }
        return application;
    }





}
