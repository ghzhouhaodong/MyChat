package com.zhd.lenovo.mychat.activirys;

import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.base.IActivity;

public class ChatActivity extends IActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Intent intent = getIntent();
            String userid= intent.getStringExtra("userid");
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userid);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.chat_frame, chatFragment).commit();


    }
}
