package com.zhd.lenovo.mychat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zhd.lenovo.mychat.activirys.LoginActivity;
import com.zhd.lenovo.mychat.activirys.RegisterActivity;
import com.zhd.lenovo.mychat.activirys.TabActivity;
import com.zhd.lenovo.mychat.base.AppManager;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SplashActivity extends Activity {

    @BindView(R.id.login_btn_main)
    Button loginBtnMain;
    @BindView(R.id.register_btn_main)
    Button registerBtnMain;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
  if (aMapLocation!=null){
     if(aMapLocation.getErrorCode()==0){
      //获得纬度

         double latitude = aMapLocation.getLatitude();
         //获得经度
         double longitude = aMapLocation.getLongitude();
  //Toast.makeText(SplashActivity.this, ""+latitude+longitude, Toast.LENGTH_SHORT).show();
         PreferencesUtils.addConfigInfo(IApplication.getApplication(),"lat",latitude+"");
         PreferencesUtils.addConfigInfo(IApplication.getApplication(),"lng",longitude+"");

     }else{
         Log.e("AmapError","location Error, ErrCode:"
                 + aMapLocation.getErrorCode() + ", errInfo:"
                 + aMapLocation.getErrorInfo());
     }
      mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
      mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。


  }
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);


  SplashActivityPermissionsDispatcher.getLocationWithCheck(this);


    }

    @OnClick({R.id.login_btn_main, R.id.register_btn_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_main:
                if(PreferencesUtils.getValueByKey(SplashActivity.this,"islogin",false)){
                    startActivity(new Intent(this,TabActivity.class));
                    finish();
                }


        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
          finish();
                break;
            case R.id.register_btn_main:
      startActivity(new Intent(SplashActivity.this, RegisterActivity.class) );
       finish();
                break;
        }
    }

    //注释执行需要一个或多个权限的操作的方法
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE})
    public void getLocation(){
        System.out.println("loginBtnMain = " + "这个方法执行了");
        //初始化定位
        mLocationClient = new AMapLocationClient(IApplication.getApplication());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();

    }
    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE})
    public void onDenied(){
        Toast.makeText(this, "onDenied", Toast.LENGTH_SHORT).show();
    }
    //注释一个方法，如果用户选择让设备“不再询问”一个权限，则调用该方法
    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE})
    public void onNeverAskAgain(){
        Toast.makeText(this, "onNeverAskAgain", Toast.LENGTH_SHORT).show();
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }
//注释一个方法，

@OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE})
public void showRationaleForCamera(final PermissionRequest request){
    new AlertDialog.Builder(this).setTitle("提示").setMessage("我们需要您的位置信息")
            .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 请求授权
                    request.proceed();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            request.cancel();
        }
    }).create().show();
}




    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);


    }
}
