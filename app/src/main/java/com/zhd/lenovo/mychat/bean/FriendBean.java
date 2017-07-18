package com.zhd.lenovo.mychat.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/7/15.
 */

public class FriendBean {


    /**
     * result_message : success
     * data : [{"area":"广东省-潮州市-潮安县","picWidth":720,"createtime":1500078234255,"picHeight":960,"gender":"男","lng":116.294962,"introduce":"111","imagePath":"http://qhb.2dyt.com/MyInterface/images/2bf3b140-81df-4e00-9837-d3c72bebfdf3.jpg","userId":3,"relationtime":1500084691877,"yxpassword":"73Y59hA4","relation":0,"password":"698d51a19d8a121ce581499d7b701668","lasttime":1500084570336,"phone":"13945290108","nickname":"xun","age":"20","lat":40.039205},{"area":"安徽省安庆市枞阳县","picWidth":720,"createtime":1500084408248,"picHeight":1200,"gender":"男","lng":0,"introduce":"213","imagePath":"http://qhb.2dyt.com/MyInterface/images/b24553d7-6794-45af-acf0-3a37f346a6d0.jpg","userId":41,"relationtime":1500084576398,"yxpassword":"LkFb7228","relation":0,"password":"202cb962ac59075b964b07152d234b70","lasttime":1500084423701,"phone":"13366518725","nickname":"4567","age":"51","lat":0}]
     * result_code : 200
     */

    private String result_message;
    private int result_code;
    private List<FriendDataBean> data;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public List<FriendDataBean> getData() {
        return data;
    }

    public void setData(List<FriendDataBean> data) {
        this.data = data;
    }

}
