package com.zhd.lenovo.mychat.activirys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.base.BaseMvpActivity;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.mview.LoginView;
import com.zhd.lenovo.mychat.presenter.LoginPresenter;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;
import com.zhd.lenovo.mychat.widget.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginView, LoginPresenter> implements LoginView{

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
 LoginPresenter loginPresenter=new LoginPresenter(this);


    @Override
    public LoginPresenter initPresenter() {
        return loginPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        phoneNumLogin.setText((String)PreferencesUtils.getValueByKey(IApplication.getApplication(),"phone",""));
      pwdEdittextLogin.setText((String)PreferencesUtils.getValueByKey(IApplication.getApplication(),"password",""));


         phoneNumLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
               //  imm.toggleSoftInputFromWindow(phoneNumLogin.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);

                 imm.showSoftInputFromInputMethod(phoneNumLogin.getWindowToken(), 0);
             }
         });



        phoneNumLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
         //  Toast.makeText(LoginActivity.this, "响应了配置后的按键", Toast.LENGTH_SHORT).show();
         //获取焦点
          pwdEdittextLogin.requestFocus();
         //显示软键盘
          imm.showSoftInputFromInputMethod(pwdEdittextLogin.getWindowToken(), 0);


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
         loginPresenter.setLoginView(num,pwd);


    }


    @Override
    public void usernameEmpty() {
        MyToast.makeText(IApplication.getApplication(),"用户名为空",Toast.LENGTH_SHORT);

    }

    @Override
    public void passwordEmpty() {
        MyToast.makeText(IApplication.getApplication(),"密码为空",Toast.LENGTH_SHORT);
    }

    @Override
    public void LoginSuccess(String result) {
        MyToast.makeText(IApplication.getApplication(),result,Toast.LENGTH_SHORT);
 if(result.equals("登录成功")){
  //跳转到主导航
   // yxpassword   uid
     String yxpassword = PreferencesUtils.getValueByKey(LoginActivity.this, "yxpassword", "0");
     String uid = PreferencesUtils.getValueByKey(LoginActivity.this, "uid", "0");
     Toast.makeText(LoginActivity.this, ""+yxpassword+uid, Toast.LENGTH_SHORT).show();
   if (!yxpassword.equals("")&&!uid.equals("")) {
  //     Toast.makeText(IApplication.getApplication(), "denl", Toast.LENGTH_SHORT).show();
       EMClient.getInstance().login(uid, yxpassword, new EMCallBack() {//回调

           @Override
           public void onSuccess() {
               EMClient.getInstance().groupManager().loadAllGroups();
               EMClient.getInstance().chatManager().loadAllConversations();
               Log.d("main", "登录聊天服务器成功！");


           }

           @Override
           public void onProgress(int progress, String status) {

           }

           @Override
           public void onError(int code, String message) {
               Log.d("main", "登录聊天服务器失败！");
              // Toast.makeText(LoginActivity.this, "denluhenggong", Toast.LENGTH_SHORT).show();
           }
       });
   }


     PreferencesUtils.addConfigInfo(LoginActivity.this,"islogin",true);
 startActivity(new Intent(LoginActivity.this,TabActivity.class));
  finish();
 }


    }

    @Override
    public void LoginFailed() {

 MyToast.makeText(IApplication.getApplication(),"登录失败，请查看网络是否连接",Toast.LENGTH_SHORT);
    }
}
