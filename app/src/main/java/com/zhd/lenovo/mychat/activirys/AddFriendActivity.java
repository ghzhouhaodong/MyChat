package com.zhd.lenovo.mychat.activirys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.base.AppManager;
import com.zhd.lenovo.mychat.base.IActivity;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.AddFirendBean;
import com.zhd.lenovo.mychat.network.BaseObserver;
import com.zhd.lenovo.mychat.network.RetrofitManager;
import com.zhd.lenovo.mychat.utils.Constants;
import com.zhd.lenovo.mychat.utils.GsonUtil;
import com.zhd.lenovo.mychat.widget.MyToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFriendActivity extends IActivity {

    @BindView(R.id.add_friend_text)
    TextView addFriendText;
    @BindView(R.id.add_friend_enter)
    Button addFriendEnter;
    private String friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        friendId = intent.getStringExtra("friendId");

        String nickname=   intent.getStringExtra("nickname");
        addFriendText.setText("   确认添加"+nickname+"为好友吗？");

    }

    @OnClick(R.id.add_friend_enter)
    public void onClick() {
        Map<String,String> map= new HashMap<String, String>();
                map.put("relationship.friendId",friendId);

        RetrofitManager.post(Constants.ADD_FRIEND, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                GsonUtil instance = GsonUtil.getInstance();
                AddFirendBean addFirendBean = instance.fromJson(result, AddFirendBean.class);
                MyToast.makeText(IApplication.getApplication(), addFirendBean.getResult_message(), Toast.LENGTH_SHORT);
       if(addFirendBean.getResult_message().equals("添加好友成功")){

         Intent intent=new Intent(AddFriendActivity.this,ChatActivity.class);
              intent.putExtra("userid",friendId);
           startActivity(intent);


           MyToast.makeText(IApplication.getApplication(),"跳转到聊天页面", Toast.LENGTH_SHORT);
       }

            }

            @Override
            public void onFailed(int code) {


            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);

    }


}
