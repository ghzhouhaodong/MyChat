package com.zhd.lenovo.mychat.activirys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.hyphenate.chat.EMClient;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.base.AppManager;
import com.zhd.lenovo.mychat.base.IActivity;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.fragments.tabfragment.FirstFragment;
import com.zhd.lenovo.mychat.fragments.tabfragment.FourthFragment;
import com.zhd.lenovo.mychat.fragments.tabfragment.SecondFragment;
import com.zhd.lenovo.mychat.fragments.tabfragment.ThiredFragment;
import com.zhd.lenovo.mychat.widget.ButtomLayout;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends IActivity implements ButtomLayout.OnSelectListener{
    private ButtomLayout buttomLayout;
    private FragmentManager fragmentManager;
    private CallReceiver callReceiver;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        fragmentManager = getSupportFragmentManager();
        createFragment(savedInstanceState);
        buttomLayout = (ButtomLayout) findViewById(R.id.buttom_layout);
        buttomLayout.setOnSelectListener(this);
        switchFragment(0);


        //监听来电
        incoming();

    }
    public void createFragment(Bundle savedInstanceState){
        FirstFragment firstFragment = (FirstFragment) fragmentManager.findFragmentByTag("FirstFragment");
        SecondFragment secondFragment = (SecondFragment) fragmentManager.findFragmentByTag("SecondFragment");
        ThiredFragment thirdFragment = (ThiredFragment) fragmentManager.findFragmentByTag("ThirdFragment");
        FourthFragment fourthFragment = (FourthFragment) fragmentManager.findFragmentByTag("FourthFragment");
        if(firstFragment == null){
            firstFragment = new FirstFragment();
        }

        if(secondFragment == null){
            secondFragment = new SecondFragment();
        }
        if(thirdFragment == null){
            thirdFragment = new ThiredFragment();
        }
        if(fourthFragment == null){
            fourthFragment = new FourthFragment();
        }


        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        fragments.add(fourthFragment);


    }
    public void switchFragment(int pos){

        switchIFragment(pos,fragments,R.id.container);

    }

    /**
     * 底部导航 点击 回调
     * @param index
     */

    @Override
    public void onSelect(int index) {

        switchFragment(index);

    }
    public void incoming() {
        callReceiver = new CallReceiver();
 IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        registerReceiver(callReceiver, callFilter);
    }




    private class CallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 拨打方username
            String from = intent.getStringExtra("from");
            // call type
            String type = intent.getStringExtra("type");
            //跳转到通话页面

            IApplication.ring();

            VideoActivity.startTelActivity(2,from,TabActivity.this);


        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager appManager = AppManager.getAppManager();
        appManager.finishActivity(this);
        unregisterReceiver(callReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
