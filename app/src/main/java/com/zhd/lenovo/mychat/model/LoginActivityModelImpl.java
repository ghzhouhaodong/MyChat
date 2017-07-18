package com.zhd.lenovo.mychat.model;

import com.google.gson.Gson;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.LoginBean;
import com.zhd.lenovo.mychat.cipher.Md5Utils;
import com.zhd.lenovo.mychat.cipher.aes.JNCryptorUtils;
import com.zhd.lenovo.mychat.cipher.rsa.RsaUtils;
import com.zhd.lenovo.mychat.network.BaseObserver;
import com.zhd.lenovo.mychat.network.RetrofitManager;
import com.zhd.lenovo.mychat.utils.Constants;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/9.
 */

public class LoginActivityModelImpl implements LoginActivityModel{


    @Override
    public void Login(String username, String pwd, final LoginActivityModelImplListener listener) {
        String randomKey= RsaUtils.getStringRandom(16);
        String rsaRandomKey =RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(),randomKey);
        String cipherPhone=      JNCryptorUtils.getInstance().encryptData(username,IApplication.getApplication(),randomKey);
        Map map = new HashMap<String,String>();
        map.put("user.phone",cipherPhone);
       //判断密码
        if(pwd.length()==32&&pwd.equals((String) PreferencesUtils.getValueByKey(IApplication.getApplication(),"password",""))){
            map.put("user.password", pwd);
        }else{
            map.put("user.password", Md5Utils.getMD5(pwd));
        }


        map.put("user.secretkey",rsaRandomKey);
        RetrofitManager.post(Constants.LOGIN_ACTION, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                Gson gson=new Gson();
                LoginBean loginBean = gson.fromJson(result, LoginBean.class);
                if(loginBean.getResult_code()==200){
                    //MyToast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT);
          listener.Success("登录成功");

                }else if(loginBean.getResult_code()==303){
           listener.Success("登录失败,用户名密码错误");

                }

            }

            @Override
            public void onFailed(int code) {
           listener.Failed();
            }
        });
    }
    public interface  LoginActivityModelImplListener{
    public void Success(String result);
     public void Failed();


    }

}
