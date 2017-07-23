package com.zhd.lenovo.mychat.activirys;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.horizontalselectedviewlibrary.HorizontalselectedView;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.base.AppManager;
import com.zhd.lenovo.mychat.base.BaseMvpActivity;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.fragments.RegisterInfoFragment;
import com.zhd.lenovo.mychat.fragments.RegisterIntroduceFragment;
import com.zhd.lenovo.mychat.fragments.RegisterSms;
import com.zhd.lenovo.mychat.mview.RegisterView;
import com.zhd.lenovo.mychat.presenter.RegisterPresenter;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;
import com.zhd.lenovo.mychat.utils.StatusBarCompat;
import com.zhd.lenovo.mychat.widget.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseMvpActivity<RegisterView, RegisterPresenter> {
    String sex;
    @BindView(R.id.back_login)
    ImageView backLogin;
    @BindView(R.id.btn_0)
    RadioButton btn0;
    @BindView(R.id.btn_1)
    RadioButton btn1;
    @BindView(R.id.hd_main)
    HorizontalselectedView horizontalselcter;
    @BindView(R.id.btn_register_enter)
    Button btnRegisterEnter;
    @BindView(R.id.radiogroup_sex)
    RadioGroup radiogroupSex;
    private List<String> list;
    private int index=5;
    private FragmentManager fragmentManager;
    private RegisterInfoFragment infoFragment;
   private List<Fragment>listf=new ArrayList<Fragment>();
    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StatusBarCompat.compat(this, getResources().getColor(R.color.backgroud));
        ButterKnife.bind(this);
             //startActivity(new Intent(RegisterActivity.this,UploadPhotoActivity.class));
         infoFragment=new RegisterInfoFragment();
        initData();
        fragmentManager = getSupportFragmentManager();
         listf.add(new RegisterSms());
            listf.add(infoFragment);
         listf.add(new RegisterIntroduceFragment());
        horizontalselcter.setData(list);
        //获取年龄
        String selectedString = horizontalselcter.getSelectedString();
    radiogroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
           switch (checkedId) {
               case R.id.btn_0:
                 index=0;
                   break;
               case R.id.btn_1:
                index=1;
                   break;
           }
        }
    });
    }
    private void initData() {
        list = new ArrayList<>();
        for (int x = 18; x < 85; x++) {
            list.add(x + "岁");
        }
    }
    @OnClick({R.id.back_login, R.id.btn_0, R.id.btn_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_login:
                finish();
                break;
            case R.id.btn_0:
                break;
            case R.id.btn_1:
                break;
        }
    }
    @OnClick(R.id.btn_register_enter)
    public void onClick() {
        String selectedString = horizontalselcter.getSelectedString();
        if(index==5){
          //  Toast.makeText(RegisterActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
            MyToast.makeText(RegisterActivity.this,"请选择性别",Toast.LENGTH_SHORT);
        }else{
      if(index ==0){
         sex="男";
      }else if(index==1){
           sex="女";
            }
 PreferencesUtils.addConfigInfo(IApplication.getApplication(),"oneage",selectedString);
 PreferencesUtils.addConfigInfo(IApplication.getApplication(),"onesex",sex);
    switchIFragment(0,listf,R.id.linear);
        }
    }
 public void setPhone(String phone){
       infoFragment.setPhone(phone);
 }
  public void setDes(String des){
        infoFragment.setDes(des);
    }
 public void toPos(int pos){
   switchIFragment(pos,listf,R.id.linear);
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager appManager = AppManager.getAppManager();

        appManager.finishActivity(this);


    }
}
