package com.zhd.lenovo.mychat.cipher.aes;

import android.content.Context;
import android.text.TextUtils;

import com.zhd.lenovo.mychat.cipher.Base64;


/**
 * Created by hongjiang on 16/5/9.
 */
public class JNCryptorUtils {

    public static JNCryptorUtils jnCryptorUtils ;

    private JNCryptorUtils(){

    }

    public  static JNCryptorUtils getInstance(){
        if (jnCryptorUtils == null) {
            jnCryptorUtils = new JNCryptorUtils();
        }
        return jnCryptorUtils;
    }

    public String decryptData(String content, Context context,String password){
        String result = "";

        try {
            if(!TextUtils.isEmpty(content)){
                com.zhd.lenovo.mychat.cipher.aes.MCrypt mCrypt = new com.zhd.lenovo.mychat.cipher.aes.MCrypt(password);
                result = new String(mCrypt.decrypt(com.zhd.lenovo.mychat.cipher.aes.MCrypt.bytesToHex(Base64.decode(content)))) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String encryptData(String content,Context context,String password){
        String result = "";
        try {
            com.zhd.lenovo.mychat.cipher.aes.MCrypt mCrypt = new com.zhd.lenovo.mychat.cipher.aes.MCrypt(password);
            result = Base64.encode(mCrypt.encrypt(content));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }



}
