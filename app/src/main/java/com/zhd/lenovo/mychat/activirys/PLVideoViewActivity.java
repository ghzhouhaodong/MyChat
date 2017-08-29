package com.zhd.lenovo.mychat.activirys;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.adapters.ZBChatcontentAdapter;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;
import com.zhd.lenovo.mychat.widget.MediaController;
import com.zhd.lenovo.mychat.widget.keyboard.KeyBoardHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a demo activity of PLVideoView
 */
public class PLVideoViewActivity extends Activity implements KeyBoardHelper.OnKeyBoardStatusChangeListener{

    private static final String TAG = PLVideoViewActivity.class.getSimpleName();

    private static final int MESSAGE_ID_RECONNECTING = 0x01;

    private MediaController mMediaController;
    private PLVideoView mVideoView;
    private Toast mToast = null;
    private String mVideoPath = null;
    private int mDisplayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT;
    private boolean mIsActivityPaused = true;
    private View mLoadingView;
    private View mCoverView = null;
    private int mIsLiveStreaming = 1;
    private EditText playedittext;
    private LinearLayout linearplay;
    private Button playpop;
    private int keyHeight;
    private ListView listView;
    private List<String> contentlist;
    private ZBChatcontentAdapter adapter;
    private String groupid;

    private void setOptions(int codecType) {
        AVOptions options = new AVOptions();

        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming);
        if (mIsLiveStreaming == 1) {
            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }

        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codecType);

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        mVideoView.setAVOptions(options);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl_video_view);
        mVideoView = (PLVideoView) findViewById(R.id.VideoView);
        playedittext = (EditText) findViewById(R.id.play_edittext_context);
        linearplay = (LinearLayout) findViewById(R.id.linear_play);
        playpop = (Button) findViewById(R.id.play_pop_exittext_btn);
           listView = (ListView) findViewById(R.id.listview_plsy);
        mCoverView = (ImageView) findViewById(R.id.CoverView);
        mVideoView.setCoverView(mCoverView);
        mLoadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(mLoadingView);
        mLoadingView.setVisibility(View.VISIBLE);

        //拉流的地址
        mVideoPath = getIntent().getStringExtra("videoPath");

        mIsLiveStreaming = getIntent().getIntExtra("liveStreaming", 1);
              String userid= getIntent().getStringExtra("zbshowid");
        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        setOptions(codec);
     //请求进群
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage("122", userid);
//如果是群聊，设置chattype，默认是单聊
//发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
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

        playpop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(playedittext.getText())){

                    keyPop();
                }else{
//创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                    EMMessage message = EMMessage.createTxtSendMessage(playedittext.getText().toString(), groupid);
//如果是群聊，设置chattype，默认是单聊

                        message.setChatType(EMMessage.ChatType.GroupChat);
//发送消息
                    EMClient.getInstance().chatManager().sendMessage(message);
                    message.setMessageStatusCallback(new EMCallBack() {
                        @Override
                        public void onSuccess() {
                contentlist.add(playedittext.getText().toString());
                           adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onError(int code, String error) {

                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }
                    });


                }



            }
        });


        // Set some listeners
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnSeekCompleteListener(mOnSeekCompleteListener);
        mVideoView.setOnErrorListener(mOnErrorListener);

        mVideoView.setVideoPath(mVideoPath);

        // You can also use a custom `MediaController` widget
        mMediaController = new MediaController(this, false, mIsLiveStreaming == 1);
        mVideoView.setMediaController(mMediaController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActivityPaused = false;
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mToast = null;
        mIsActivityPaused = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);

    }
    private void keyPop(){

        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) linearplay.getLayoutParams();
        params.setMargins(0,0,0,keyHeight);
        params.gravity = Gravity.BOTTOM;
        linearplay.setLayoutParams(params);
        playedittext.setVisibility(View.VISIBLE);
        showKeyBoard(playedittext);


    }
    private void keyHide(){
        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) linearplay.getLayoutParams();
        params.setMargins(0,0,0,0);
        params.gravity = Gravity.BOTTOM;
        linearplay.setLayoutParams(params);
    }
    public void onClickSwitchScreen(View v) {
        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mVideoView.getDisplayAspectRatio()) {
            case PLVideoView.ASPECT_RATIO_ORIGIN:
                showToastTips("Origin mode");
                break;
            case PLVideoView.ASPECT_RATIO_FIT_PARENT:
                showToastTips("Fit parent !");
                break;
            case PLVideoView.ASPECT_RATIO_PAVED_PARENT:
                showToastTips("Paved parent !");
                break;
            case PLVideoView.ASPECT_RATIO_16_9:
                showToastTips("16 : 9 !");
                break;
            case PLVideoView.ASPECT_RATIO_4_3:
                showToastTips("4 : 3 !");
                break;
            default:
                break;
        }
    }

    private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {
            Log.d(TAG, "onInfo: " + what + ", " + extra);
            return false;
        }
    };

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
            boolean isNeedReconnect = false;
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    showToastTips("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    showToastTips("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    showToastTips("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    showToastTips("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    showToastTips("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    showToastTips("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    showToastTips("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    showToastTips("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    showToastTips("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    showToastTips("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    setOptions(AVOptions.MEDIA_CODEC_SW_DECODE);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    showToastTips("unknown error !");
                    break;
            }
            // Todo pls handle the error status here, reconnect or call finish()
            if (isNeedReconnect) {
                sendReconnectMessage();
            } else {
                finish();
            }
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            return true;
        }
    };

    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            Log.d(TAG, "Play Completed !");
            showToastTips("Play Completed !");
            finish();
        }
    };

    private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new PLMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int precent) {
            Log.d(TAG, "onBufferingUpdate: " + precent);
        }
    };

    private PLMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener = new PLMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
            Log.d(TAG, "onSeekComplete !");
        }
    };

    private PLMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int videoSar, int videoDen) {
            Log.d(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height + ", sar = " + videoSar + ", den = " + videoDen);
        }
    };

    private void showToastTips(final String tips) {
        if (mIsActivityPaused) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(PLVideoViewActivity.this, tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != MESSAGE_ID_RECONNECTING) {
                return;
            }else if(msg.what==1){
                EMMessageBody body = (EMMessageBody) msg.obj;
                contentlist.add(body.toString());
                adapter.notifyDataSetChanged();


            }
//            if (mIsActivityPaused || !Utils.isLiveStreamingAvailable()) {
//                finish();
//                return;
//            }
//            if (!Utils.isNetworkAvailable(PLVideoViewActivity.this)) {
//                sendReconnectMessage();
//                return;
//            }
            mVideoView.setVideoPath(mVideoPath);
            mVideoView.start();
        }
    };

    private void sendReconnectMessage() {
        showToastTips("正在重连...");
        mLoadingView.setVisibility(View.VISIBLE);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
    }


    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        playedittext.setVisibility(View.VISIBLE);
        playedittext.requestFocus();



    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {
        playedittext.setVisibility(View.INVISIBLE);
        keyHide();


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
                Message msg = mHandler.obtainMessage();
                msg.what=1;
                msg.obj=messages.get(0).getBody();
                messages.get(0).getBody();
                mHandler.sendMessage(msg);


   }else{
                groupid = messages.get(0).getBody().toString();



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




}
