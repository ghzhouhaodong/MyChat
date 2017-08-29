package com.zhd.lenovo.mychat.activirys;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.adapters.ZBChatcontentAdapter;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;
import com.zhd.lenovo.mychat.widget.keyboard.KeyBoardHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SWCameraStreamingActivity extends Activity implements StreamingStateChangedListener,KeyBoardHelper.OnKeyBoardStatusChangeListener {
    private JSONObject mJSONObject;
    private MediaStreamingManager mMediaStreamingManager;
    private StreamingProfile mProfile;
    private ListView listView;
    private List<String> contentlist;
    private ZBChatcontentAdapter adapter;
    private LinearLayout linearLayout;
    private Button showpopbtn;
    private int keyHeight;
    private EditText showedittext;
    private EMGroup group;
   private Handler handler =new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
     if(msg.what==1){
         EMMessageBody body = (EMMessageBody) msg.obj;
             contentlist.add(body.toString());
          adapter.notifyDataSetChanged();

     }else if(msg.what==2){
          String  cont= (String) msg.obj;
          contentlist.add(cont);
  adapter.notifyDataSetChanged();
           }




       }
   };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swcamera_streaming);
        AspectFrameLayout afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
        linearLayout = (LinearLayout) findViewById(R.id.linear_show);
        showedittext = (EditText) findViewById(R.id.show_edittext_context);

        showpopbtn = (Button) findViewById(R.id.show_pop_exittext_btn);
        // Decide FULL screen or real size
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);
        GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.cameraPreview_surfaceView);
        listView = (ListView) findViewById(R.id.listview_show);
        keyHeight = PreferencesUtils.getValueByKey(this, "kh", 556);
        contentlist = new ArrayList<String>();
        contentlist.add("开播了吗");
        contentlist.add("开播了吗2");
        contentlist.add("开播了吗2");
        contentlist.add("开播了吗2");
        contentlist.add("开播了吗2");
        contentlist.add("开播了吗2");
        contentlist.add("开播了吗2");
        contentlist.add("开播了吗2");
        contentlist.add("开播了吗2");
        contentlist.add("开播了吗2");
        adapter = new ZBChatcontentAdapter(this,contentlist);
         listView.setAdapter(adapter);
        KeyBoardHelper keyBoardHelper=new KeyBoardHelper(this);
             keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);
        //注册消息监听
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

        showpopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(TextUtils.isEmpty(showedittext.getText())){

                   keyPop();
               }else if(showedittext.getText().toString()!=null){
              //添加到list



               }



            }
        });
        EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
        option.maxUsers = 200;
        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;

        try {
            group = EMClient.getInstance().groupManager().createGroup("我的群", "简介", new String[]{}, "sd", option);



        } catch (HyphenateException e) {
            e.printStackTrace();
        }


        String streamJsonStrFromServer = getIntent().getStringExtra("stream_json_str");
        try {
//            mJSONObject = new JSONObject(streamJsonStrFromServer);
//            StreamingProfile.Stream stream = new StreamingProfile.Stream(mJSONObject);
            mProfile = new StreamingProfile();
            //推流的地址
            mProfile.setPublishUrl(streamJsonStrFromServer);
            mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH1)
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY);
//                    .setStream(stream);  // You can invoke this before startStreaming, but not in initialization phase.
            CameraStreamingSetting setting = new CameraStreamingSetting();
            setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
                    .setContinuousFocusModeEnabled(true)
                    .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                    .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9);

            //美颜
            setting.setBuiltInFaceBeautyEnabled(true);
            setting.setFaceBeautySetting(new CameraStreamingSetting.FaceBeautySetting(1.0f, 1.0f, 0.8f))
                    .setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY);

            mMediaStreamingManager = new MediaStreamingManager(this, afl, glSurfaceView, AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
            mMediaStreamingManager.prepare(setting, mProfile);
            mMediaStreamingManager.setStreamingStateListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaStreamingManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // You must invoke pause here.
        mMediaStreamingManager.pause();
    }

    @Override
    public void onStateChanged(StreamingState streamingState, Object extra) {
        switch (streamingState) {
            case PREPARING:
                break;
            case READY:
                // start streaming when READY
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMediaStreamingManager != null) {
                            mMediaStreamingManager.startStreaming();

                        }
                    }
                }).start();
                break;
            case CONNECTING:
                break;
            case STREAMING:
                // The av packet had been sent.
                break;
            case SHUTDOWN:
                // The streaming had been finished.
                break;
            case IOERROR:
                // Network connect error.
                break;
            case OPEN_CAMERA_FAIL:
                // Failed to open camera.
                break;
            case DISCONNECTED:
                // The socket is broken while streaming
                break;
        }
    }
 private void keyPop(){

     FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
     params.setMargins(0,0,0,keyHeight);
      params.gravity = Gravity.BOTTOM;
      linearLayout.setLayoutParams(params);
     showedittext.setVisibility(View.VISIBLE);
     showKeyBoard(showedittext);


 }
    private void keyHide(){
        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMargins(0,0,0,0);
        params.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(params);
    }



    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        showedittext.setVisibility(View.VISIBLE);
           showedittext.requestFocus();

       }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {
        showedittext.setVisibility(View.INVISIBLE);
            keyHide();

    }
    //隐藏键盘

    public void hidenKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);


    }

    public void showKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      //  imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    imm.toggleSoftInputFromWindow(editText.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);


    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            EMMessage.ChatType chatType = messages.get(0).getChatType();
            EMMessage.Type type = messages.get(0).getType();

            if(chatType.equals(EMMessage.ChatType.GroupChat)&&type.equals(EMMessage.Type.TXT)){
                Message msg = handler.obtainMessage();
                msg.what=1;
                msg.obj=messages.get(0).getBody();
                 messages.get(0).getBody();
                handler.sendMessage(msg);




            }else{





            String from = messages.get(0).getFrom();

   try {
    EMClient.getInstance().groupManager().addUsersToGroup(group.getGroupId(), new String[]{from});//需异步处理
       //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
       EMMessage message = EMMessage.createTxtSendMessage(group.getGroupId(),from);
//如果是群聊，设置chattype，默认是单聊


//发送消息
       EMClient.getInstance().chatManager().sendMessage(message);




    } catch (HyphenateException e) {
                e.printStackTrace();
            }
            }

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }


        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };




    @Override
    protected void onDestroy() {
        super.onDestroy();

        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        try {
            EMClient.getInstance().groupManager().destroyGroup(group.getGroupId());//需异步处理
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
}