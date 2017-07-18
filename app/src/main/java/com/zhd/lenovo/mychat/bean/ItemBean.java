package com.zhd.lenovo.mychat.bean;

/**
 * Created by lenovo on 2017/7/13.
 */

 public class ItemBean {


    /**
     * result_message : success
     * data : {"area":"河南省 焦作市 孟州市","lasttime":1499910786050,"createtime":1499830426052,"gender":"男","introduce":"死肥宅就是我。","imagePath":"http://qhb.2dyt.com/MyInterface/images/8618fc75-4efa-4f17-a777-aad915fe249b.jpg","nickname":"Reinhardt","userId":21,"photolist":[{"picWidth":720,"timer":1499842558230,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/e2335022-7880-4a24-a987-4bba7abea51f.jpg","albumId":5,"userId":21},{"picWidth":720,"timer":1499842685715,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/533919ea-541b-45fb-971e-9e8af5c87c42.jpg","albumId":6,"userId":21},{"picWidth":720,"timer":1499842719930,"picHeight":639,"imagePath":"http://qhb.2dyt.com/MyInterface/images/9d0f042d-267e-4850-b7d4-aee8a4ec65c5.jpg","albumId":7,"userId":21},{"picWidth":720,"timer":1499842733598,"picHeight":604,"imagePath":"http://qhb.2dyt.com/MyInterface/images/b94d09a0-00b9-4ae3-bf82-6efc26a3e8b5.jpg","albumId":8,"userId":21},{"picWidth":720,"timer":1499842742076,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/582dcdc1-3721-4b74-9204-eb171cade580.jpg","albumId":9,"userId":21},{"picWidth":720,"timer":1499860969983,"picHeight":721,"imagePath":"http://qhb.2dyt.com/MyInterface/images/7553f969-18e1-448d-89d4-dc56fbacfe7b.jpg","albumId":22,"userId":21},{"picWidth":720,"timer":1499860975441,"picHeight":976,"imagePath":"http://qhb.2dyt.com/MyInterface/images/20600157-87d8-4189-aea2-17bc533a1f10.jpg","albumId":23,"userId":21},{"picWidth":720,"timer":1499860979230,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/ad83b9d1-f086-48df-bda5-96b27863cbc5.jpg","albumId":24,"userId":21},{"picWidth":720,"timer":1499865951043,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/a1058b3c-0b36-4e2c-886b-6c45abe6781c.jpg","albumId":41,"userId":21},{"picWidth":720,"timer":1499865955043,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/fd6dcdda-2775-4c97-aab6-f38c4f2ccd1e.jpg","albumId":42,"userId":21}]}
     * result_code : 200
     */

    private String result_message;
    private DataItemBean data;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public DataItemBean getData() {
        return data;
    }

    public void setData(DataItemBean data) {
        this.data = data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

}
