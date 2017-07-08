package com.zhd.lenovo.mychat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhd.lenovo.mychat.activirys.LoginActivity;
import com.zhd.lenovo.mychat.activirys.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends Activity {

    @BindView(R.id.login_btn_main)
    Button loginBtnMain;
    @BindView(R.id.register_btn_main)
    Button registerBtnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        System.loadLibrary("core");

    }

    @OnClick({R.id.login_btn_main, R.id.register_btn_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_main:
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                break;
            case R.id.register_btn_main:
      startActivity(new Intent(SplashActivity.this, RegisterActivity.class) );

                break;
        }
    }
}
