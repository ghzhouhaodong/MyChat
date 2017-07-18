package com.zhd.lenovo.mychat.activirys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.zhd.lenovo.mychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllPhotoActivity extends Activity {

    @BindView(R.id.photo_view_activity)
    PhotoView photoViewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_photo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String imagephotoview = intent.getStringExtra("imagephotoview");

        Glide.with(this).load(imagephotoview).into(photoViewActivity);



    }





}
