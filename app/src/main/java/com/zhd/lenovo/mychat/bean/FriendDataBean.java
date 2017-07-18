package com.zhd.lenovo.mychat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FriendDataBean {
        /**
         * area : 广东省-潮州市-潮安县
         * picWidth : 720
         * createtime : 1500078234255
         * picHeight : 960
         * gender : 男
         * lng : 116.294962
         * introduce : 111
         * imagePath : http://qhb.2dyt.com/MyInterface/images/2bf3b140-81df-4e00-9837-d3c72bebfdf3.jpg
         * userId : 3
         * relationtime : 1500084691877
         * yxpassword : 73Y59hA4
         * relation : 0
         * password : 698d51a19d8a121ce581499d7b701668
         * lasttime : 1500084570336
         * phone : 13945290108
         * nickname : xun
         * age : 20
         * lat : 40.039205
         */
        @Id(autoincrement = true)
        private Long id;
        private String area;
        private int picWidth;
        private long createtime;
        private int picHeight;
        private String gender;
        private double lng;
        private String introduce;
        private String imagePath;
        private int userId;
        private long relationtime;
        private String yxpassword;
        private int relation;
        private String password;
        private long lasttime;
        private String phone;
        private String nickname;
        private String age;
        private double lat;

        @Generated(hash = 880992758)
        public FriendDataBean(Long id, String area, int picWidth, long createtime, int picHeight,
                String gender, double lng, String introduce, String imagePath, int userId,
                long relationtime, String yxpassword, int relation, String password, long lasttime,
                String phone, String nickname, String age, double lat) {
            this.id = id;
            this.area = area;
            this.picWidth = picWidth;
            this.createtime = createtime;
            this.picHeight = picHeight;
            this.gender = gender;
            this.lng = lng;
            this.introduce = introduce;
            this.imagePath = imagePath;
            this.userId = userId;
            this.relationtime = relationtime;
            this.yxpassword = yxpassword;
            this.relation = relation;
            this.password = password;
            this.lasttime = lasttime;
            this.phone = phone;
            this.nickname = nickname;
            this.age = age;
            this.lat = lat;
        }

        @Generated(hash = 704863042)
        public FriendDataBean() {
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getPicWidth() {
            return picWidth;
        }

        public void setPicWidth(int picWidth) {
            this.picWidth = picWidth;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getPicHeight() {
            return picHeight;
        }

        public void setPicHeight(int picHeight) {
            this.picHeight = picHeight;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getRelationtime() {
            return relationtime;
        }

        public void setRelationtime(long relationtime) {
            this.relationtime = relationtime;
        }

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public Long getId() {
            return this.id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
