package com.zhd.lenovo.mychat.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.utils.StatusBarCompat;

import java.util.List;

public class IActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewTitle;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_i);
        StatusBarCompat.compat(this, getResources().getColor(R.color.backgroud));

        fragmentManager = getSupportFragmentManager();
    //把已打开的activity添加到
 // AppManager.getAppManager().addActivity(this);


    }
    /**
     * 切换fragment
     * @param pos
     * @param list
     */
    public void switchIFragment(int pos, List<Fragment> list, int containerId){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!list.get(pos).isAdded()) {
            fragmentTransaction.add(containerId, list.get(pos),list.get(pos).getClass().getSimpleName());
        }
        for(int i=0;i<list.size();i++){
            if(i == pos){
                fragmentTransaction.show(list.get(pos));
            }else {
                fragmentTransaction.hide(list.get(i));
            }
        }
        fragmentTransaction.commit();
    }


    public void setIPubTitle(String title){
        textViewTitle = (TextView) findViewById(R.id.pub_title_title);
        textViewTitle.setText(title);
    }

    public void setILeftBtn(){
        Button btnRight = (Button) findViewById(R.id.pub_title_leftbtn);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    public void toIActivity(Class clazz , Bundle bundle, int requestCode){
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if(requestCode == 0){
            startActivity(intent);
        }else {
            startActivityForResult(intent,requestCode);
        }
    }

    @Override
    public void onClick(View v) {

    }




}
