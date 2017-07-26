package com.zhd.lenovo.mychat.base;

import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.mob.MobApplication;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.dao.DaoMaster;
import com.zhd.lenovo.mychat.dao.DaoSession;

import java.util.Iterator;
import java.util.List;

import static com.hyphenate.chat.EMGCMListenerService.TAG;


public class IApplication extends MobApplication {


    public static IApplication application ;
    public static DaoSession daoSession ;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Fresco.initialize(this);

        //加载so文件库
        System.loadLibrary("core");
        System.loadLibrary("speex");
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(application.getPackageName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

         initEM();




    initGreendao();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
       // 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        CrashReport.initCrashReport(getApplicationContext(), "d2238b3105", true);


    }

    private void initEM() {

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
       // EaseUI.getInstance().init(application,options);
//初始化
       EMClient.getInstance().init(application, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }


    public static IApplication getApplication(){
        if(application == null){
            application = getApplication() ;
        }
        return application;
    }

    public void initGreendao(){


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"mychat.db");
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        //   加密
//        DaoMaster master = new DaoMaster(helper.getEncryptedWritableDb("1111"));

        daoSession = master.newSession() ;

    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }



    public static void ring(){
        SoundPool soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);

        soundPool.load(application, R.raw.avchat_ring,1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,1, 1, 0, 0, 1);
            }
        });

    }


    public static void callTo(){
        SoundPool soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);

        soundPool.load(application, R.raw.avchat_connecting,1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,1, 1, 0, 0, 1);
            }
        });


    }


}
