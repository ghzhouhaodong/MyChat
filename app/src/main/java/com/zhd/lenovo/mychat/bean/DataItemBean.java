package com.zhd.lenovo.mychat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
 @Entity
 public  class DataItemBean {
        /**
         * area : 河南省 焦作市 孟州市
         * lasttime : 1499910786050
         * createtime : 1499830426052
         * gender : 男
         * introduce : 死肥宅就是我。
         * imagePath : http://qhb.2dyt.com/MyInterface/images/8618fc75-4efa-4f17-a777-aad915fe249b.jpg
         * nickname : Reinhardt
         * userId : 21
         * photolist : [{"picWidth":720,"timer":1499842558230,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/e2335022-7880-4a24-a987-4bba7abea51f.jpg","albumId":5,"userId":21},{"picWidth":720,"timer":1499842685715,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/533919ea-541b-45fb-971e-9e8af5c87c42.jpg","albumId":6,"userId":21},{"picWidth":720,"timer":1499842719930,"picHeight":639,"imagePath":"http://qhb.2dyt.com/MyInterface/images/9d0f042d-267e-4850-b7d4-aee8a4ec65c5.jpg","albumId":7,"userId":21},{"picWidth":720,"timer":1499842733598,"picHeight":604,"imagePath":"http://qhb.2dyt.com/MyInterface/images/b94d09a0-00b9-4ae3-bf82-6efc26a3e8b5.jpg","albumId":8,"userId":21},{"picWidth":720,"timer":1499842742076,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/582dcdc1-3721-4b74-9204-eb171cade580.jpg","albumId":9,"userId":21},{"picWidth":720,"timer":1499860969983,"picHeight":721,"imagePath":"http://qhb.2dyt.com/MyInterface/images/7553f969-18e1-448d-89d4-dc56fbacfe7b.jpg","albumId":22,"userId":21},{"picWidth":720,"timer":1499860975441,"picHeight":976,"imagePath":"http://qhb.2dyt.com/MyInterface/images/20600157-87d8-4189-aea2-17bc533a1f10.jpg","albumId":23,"userId":21},{"picWidth":720,"timer":1499860979230,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/ad83b9d1-f086-48df-bda5-96b27863cbc5.jpg","albumId":24,"userId":21},{"picWidth":720,"timer":1499865951043,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/a1058b3c-0b36-4e2c-886b-6c45abe6781c.jpg","albumId":41,"userId":21},{"picWidth":720,"timer":1499865955043,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/fd6dcdda-2775-4c97-aab6-f38c4f2ccd1e.jpg","albumId":42,"userId":21}]
         */

    @Id(autoincrement = true)
     private Long id;
        private String area;
        private long lasttime;
        private long createtime;
        private String gender;
        private String introduce;
        private String imagePath;
        private String nickname;
        private int  relation;
        private int userId;
     @Transient
        private List<PhotolistBean> photolist;

    @Generated(hash = 1499865920)
    public DataItemBean(Long id, String area, long lasttime, long createtime, String gender, String introduce, String imagePath, String nickname, int relation, int userId) {
        this.id = id;
        this.area = area;
        this.lasttime = lasttime;
        this.createtime = createtime;
        this.gender = gender;
        this.introduce = introduce;
        this.imagePath = imagePath;
        this.nickname = nickname;
        this.relation = relation;
        this.userId = userId;
    }

    @Generated(hash = 1291576934)
    public DataItemBean() {
    }

     public int getRelation() {
         return relation;
     }

     public void setRelation(int relation) {
         this.relation = relation;
     }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<PhotolistBean> getPhotolist() {
            return photolist;
        }

        public void setPhotolist(List<PhotolistBean> photolist) {
            this.photolist = photolist;
        }

        public Long getId() {
            return this.id;
        }

        public void setId(Long id) {
            this.id = id;
        }

    }
