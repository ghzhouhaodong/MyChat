package com.zhd.lenovo.mychat.utils;


import com.google.gson.Gson;

/**
 * description
 */

public class GsonUtil {
    private static volatile GsonUtil gsonUtil = null;
    private static Gson gson;

    private GsonUtil() {
        gson = new Gson();
    }

    public static GsonUtil getInstance() {
        if (gsonUtil == null) {
            synchronized (GsonUtil.class) {
                if (gsonUtil == null) {
                    gsonUtil = new GsonUtil();
                }
            }
        }

        return gsonUtil;
    }
    public <T> T fromJson(String s,Class<T> classType){
        T t = gson.fromJson(s, classType);
        return t;

    }
}
