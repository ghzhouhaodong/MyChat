package com.zhd.lenovo.mychat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public  class PhotolistBean {
            /**
             * picWidth : 720
             * timer : 1499842558230
             * picHeight : 720
             * imagePath : http://qhb.2dyt.com/MyInterface/images/e2335022-7880-4a24-a987-4bba7abea51f.jpg
             * albumId : 5
             * userId : 21
             */
        @Id(autoincrement = true)
            private Long id;
            private int picWidth;
            private long timer;
            private int picHeight;
            private String imagePath;
            private int albumId;
            private int userId;

            @Generated(hash = 265033934)
            public PhotolistBean(Long id, int picWidth, long timer, int picHeight, String imagePath, int albumId,
                    int userId) {
                this.id = id;
                this.picWidth = picWidth;
                this.timer = timer;
                this.picHeight = picHeight;
                this.imagePath = imagePath;
                this.albumId = albumId;
                this.userId = userId;
            }

            @Generated(hash = 730048812)
            public PhotolistBean() {
            }

            public int getPicWidth() {
                return picWidth;
            }

            public void setPicWidth(int picWidth) {
                this.picWidth = picWidth;
            }

            public long getTimer() {
                return timer;
            }

            public void setTimer(long timer) {
                this.timer = timer;
            }

            public int getPicHeight() {
                return picHeight;
            }

            public void setPicHeight(int picHeight) {
                this.picHeight = picHeight;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public int getAlbumId() {
                return albumId;
            }

            public void setAlbumId(int albumId) {
                this.albumId = albumId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public Long getId() {
                return this.id;
            }

            public void setId(Long id) {
                this.id = id;
            }
        }
