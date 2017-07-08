package com.zhd.lenovo.mychat.activirys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.base.BaseMvpActivity;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.LoginBean;
import com.zhd.lenovo.mychat.cipher.Md5Utils;
import com.zhd.lenovo.mychat.cipher.aes.JNCryptorUtils;
import com.zhd.lenovo.mychat.cipher.rsa.RsaUtils;
import com.zhd.lenovo.mychat.mview.LoginView;
import com.zhd.lenovo.mychat.network.BaseObserver;
import com.zhd.lenovo.mychat.network.RetrofitManager;
import com.zhd.lenovo.mychat.presenter.LoginPresenter;
import com.zhd.lenovo.mychat.utils.Constants;
import com.zhd.lenovo.mychat.widget.MyToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginView, LoginPresenter> {

    @BindView(R.id.back_login)
    ImageView backLogin;
    private InputMethodManager imm;
    @BindView(R.id.phone_num_login)
    EditText phoneNumLogin;
    @BindView(R.id.pwd_edittext_login)
    EditText pwdEdittextLogin;
    @BindView(R.id.login_two_btn)
    Button loginTwoBtn;
    @BindView(R.id.goto_register)
    TextView gotoRegister;

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);



         phoneNumLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
                 imm.toggleSoftInputFromWindow(phoneNumLogin.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);

             }
         });



        phoneNumLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
         //  Toast.makeText(LoginActivity.this, "响应了配置后的按键", Toast.LENGTH_SHORT).show();
         //获取焦点
          pwdEdittextLogin.requestFocus();

          return true;


      }
  });





    }

    @OnClick({R.id.back_login, R.id.login_two_btn, R.id.goto_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_login:

                  finish();
                break;
            case R.id.login_two_btn:

           gotoLogin();
                imm.hideSoftInputFromWindow(loginTwoBtn.getWindowToken(), 0);
                break;
            case R.id.goto_register:

             startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
                break;
        }
    }

    private void gotoLogin() {
      String num=  phoneNumLogin.getText().toString().trim();
    String pwd=  pwdEdittextLogin.getText().toString().trim();
         String randomKey= RsaUtils.getStringRandom(16);
      String rsaRandomKey =RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(),randomKey);
  String cipherPhone=      JNCryptorUtils.getInstance().encryptData(num,IApplication.getApplication(),randomKey);
        Map map = new HashMap<String,String>();
        map.put("user.phone",cipherPhone);
        map.put("user.password", Md5Utils.getMD5(pwd));
        map.put("user.secretkey",rsaRandomKey);
        RetrofitManager.post(Constants.LOGIN_ACTION, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                Gson gson=new Gson();
                LoginBean loginBean = gson.fromJson(result, LoginBean.class);
           if(loginBean.getResult_code()==200){
               MyToast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT);




           }




            }

            @Override
            public void onFailed(int code) {

            }
        });


    }



}
