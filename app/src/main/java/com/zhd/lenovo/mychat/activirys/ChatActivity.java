package com.zhd.lenovo.mychat.activirys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenuBase;
import com.socks.library.KLog;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.adapters.MyChatAdapter;
import com.zhd.lenovo.mychat.base.AppManager;
import com.zhd.lenovo.mychat.base.IActivity;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.speex.SpeexPlayer;
import com.zhd.lenovo.mychat.speex.SpeexRecorder;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;
import com.zhd.lenovo.mychat.utils.SDCardUtils;
import com.zhd.lenovo.mychat.widget.EditTextPreIme;
import com.zhd.lenovo.mychat.widget.MyToast;
import com.zhd.lenovo.mychat.widget.keyboard.KeyBoardHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhd.lenovo.mychat.R.id.chat_btn_sendvoice;
import static com.zhd.lenovo.mychat.widget.keyboard.KeyBoardHelper.OnKeyBoardStatusChangeListener;


public class ChatActivity extends IActivity implements OnKeyBoardStatusChangeListener, EditTextPreIme.EditTextListener,MyChatAdapter.OnItemClickListener{

    @BindView(R.id.back_login_four)
    ImageView backLoginFour;
    @BindView(chat_btn_sendvoice)
    Button chatBtnSendvoice;

    @BindView(R.id.chat_btn_biaoqing)
    Button chatBtnBiaoqing;
    @BindView(R.id.chat_btn_sendtext)
    TextView chatBtnSendtext;
    @BindView(R.id.buttom_layout_view)
    LinearLayout buttomLayoutView;
    @BindView(R.id.chat_btn_jia)
    Button chatBtnJia;
    @BindView(R.id.chat_list)
    ListView chatListView;
    protected MyChatAdapter adapter = null;
    EaseEmojiconMenu emojiconMenu;
    public final static int OTHER = 1;
    public final static int ME = 0;
    ArrayList<HashMap<String, Object>> chatList = null;
    String[] from = {"image", "text"};
    int[] to = {R.id.chatlist_image_me, R.id.chatlist_text_me, R.id.chatlist_image_other, R.id.chatlist_text_other};
    int[] layout = {R.layout.chatlist_for_me, R.layout.chatlist_for_you};
    String userQQ = null;
    @BindView(R.id.chat_edittext)
    EditTextPreIme chatEdittext;
    @BindView(R.id.linear_addvoicebutton)
    LinearLayout linearAddvoicebutton;
  /*  @BindView(R.id.voice_btn_chat)
    Button voiceBtnChat;*/

    private EMMessageListener msgListener;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {


                EMTextMessageBody txtBody = (EMTextMessageBody) msg.obj;

                addTextToList(txtBody.getMessage(), OTHER);

                adapter.notifyDataSetChanged();
                chatListView.setSelection(chatList.size() - 1);
            } else if (msg.what == 2) {
                EMTextMessageBody txtBody = (EMTextMessageBody) msg.obj;

                addTextToList(txtBody.getMessage(), ME);
                adapter.notifyDataSetChanged();
                chatListView.setSelection(chatList.size() - 1);
            }else if(msg.what==3){
                String filename2= (String) msg.obj;
      EMMessage emMessage =EMMessage.createVoiceSendMessage(filename2,20,userid);
                EMClient.getInstance().chatManager().sendMessage(emMessage);
              //  SpeexPlayer player = new SpeexPlayer(filename2,handler);
               // player.startPlay();
                addTextToList(filename2, ME);
                adapter.notifyDataSetChanged();
                chatListView.setSelection(chatList.size() - 1);


            }else if(msg.what==4){
                EMVoiceMessageBody voiceMessageBody= (EMVoiceMessageBody) msg.obj;
                 addTextToList( voiceMessageBody.getLocalUrl(),OTHER);
                adapter.notifyDataSetChanged();
                chatListView.setSelection(chatList.size() - 1);

            }


            super.handleMessage(msg);
        }
    };
    private String userid;
    private String myid;
    private Button button;
    private SpeexRecorder recorderInstance;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        chatList = new ArrayList<HashMap<String, Object>>();
       /* addTextToList("不管你是谁", ME);
        addTextToList("群发的我不回\n  ^_^", OTHER);
        addTextToList("哈哈哈哈", ME);
        addTextToList("新年快乐！", OTHER);*/
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        myid = PreferencesUtils.getValueByKey(this, "myid", "0");
        adapter = new MyChatAdapter(this, chatList, layout, from, to);

        button = new Button(this);

        button.setText(" 按住说话 ");
        button.setGravity(Gravity.CENTER);
        button.setPadding(5,5,5,5);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        String filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
                        System.out.println("filePath:" + filePath);
                        File file = new File(filePath  + "/");
                        System.out.println("file:" + file);
                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        fileName = file + File.separator + System.currentTimeMillis() + ".spx";
                        System.out.println("保存文件名：＝＝ " + fileName);
                        recorderInstance = new SpeexRecorder(fileName, handler);
                        Thread th = new Thread(recorderInstance);
                        th.start();
                        recorderInstance.setRecording(true);

                        break;
                    case MotionEvent.ACTION_MOVE:




                break;


               case MotionEvent.ACTION_UP:
                   recorderInstance.setRecording(false);

                   System.out.println("fileName = " + new File(fileName).length());
            Message message=new Message();
               message.what=3;
             message.obj=fileName;
            handler.sendMessage(message);

                break;

                }




                return true;
            }
        });


        chatBtnSendtext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String myWord = null;
                setTextMessage();
                /**
                 * 这是一个发送消息的监听器，注意如果文本框中没有内容，那么getText()的返回值可能为
                 * null，这时调用toString()会有异常！所以这里必须在后面加上一个""隐式转换成String实例
                 * ，并且不能发送空消息。
                 */

                myWord = (chatEdittext.getText() + "").toString();
                if (myWord.length() == 0)
                    return;
                chatEdittext.setText("");
                addTextToList(myWord, ME);
                /**
                 * 更新数据列表，并且通过setSelection方法使ListView始终滚动在最底端
                 */
                adapter.notifyDataSetChanged();
                chatListView.setSelection(chatList.size() - 1);

            }
        });

        int keyHeight = PreferencesUtils.getValueByKey(this, "kh", 300);
        chatEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (chatEdittext.getText().length() > 0) {

                    chatBtnSendtext.setVisibility(View.VISIBLE);
                    chatBtnJia.setVisibility(View.INVISIBLE);

                } else {
                    chatBtnSendtext.setVisibility(View.INVISIBLE);
                    chatBtnJia.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) buttomLayoutView.getLayoutParams();
        params.height = keyHeight;
        buttomLayoutView.setLayoutParams(params);


        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this);
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);


        // EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数

       /* Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userid);
        chatFragment.setArguments(args);*/
        chatEdittext.setListener(this);
        chatEdittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (buttomLayoutView.getVisibility() == View.VISIBLE) {
                    setKeyBoardModelPan();
                } else {
                    setKeyBoardModelResize();
                }
                chatEdittext.setListener(ChatActivity.this);
                return false;
            }
        });
        chatBtnBiaoqing.setTag(1);
        chatListView.setAdapter(adapter);
        receive();
        initEmoje(null);
        initListener();
      adapter.setOnItemClickListener(this);

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userid);
//获取此会话的所有消息
        if (conversation != null) {
            List<EMMessage> messages = conversation.getAllMessages();
            for (int x = 0; x < messages.size(); x++) {

                String to = messages.get(x).getTo();
                String from = messages.get(x).getFrom();
                KLog.d(to + from);
                EMMessage.Type type = messages.get(0).getType();
                 if(type.equals(EMMessage.Type.TXT)){




                if (from.equals(myid) && to.equals(userid)) {



                    Message obtain = Message.obtain();
                    obtain.what = 2; //我
                    //   MyToast.makeText(IApplication.getApplication(),to+userid, Toast.LENGTH_SHORT);
                    obtain.obj = messages.get(x).getBody();
                    handler.sendMessage(obtain);


                } else if (to.equals(myid) && from.equals(userid)) {
                    Message obtain = Message.obtain();
                    obtain.what = 1;   //对方
                    MyToast.makeText(IApplication.getApplication(), from + userid, Toast.LENGTH_SHORT);
                    obtain.obj = messages.get(x).getBody();
                    handler.sendMessage(obtain);

                }

                 }else if(type.equals(EMMessage.Type.VOICE)){
                     if (from.equals(myid) && to.equals(userid)) {
                         Message obtain = Message.obtain();
                         obtain.what = 3;
                         //   MyToast.makeText(IApplication.getApplication(),to+userid, Toast.LENGTH_SHORT);
                         EMVoiceMessageBody body= (EMVoiceMessageBody) messages.get(x).getBody();
                         obtain.obj = body.getLocalUrl();
                         handler.sendMessage(obtain);
                     } else if (to.equals(myid) && from.equals(userid)) {
                         Message obtain = Message.obtain();
                         obtain.what = 4;   //对方
                         MyToast.makeText(IApplication.getApplication(), from + userid, Toast.LENGTH_SHORT);
                         obtain.obj = messages.get(x).getBody();
                         handler.sendMessage(obtain);
                     }





                 }



            }

        }


    }

    @OnClick({R.id.back_login_four, chat_btn_sendvoice, R.id.chat_btn_biaoqing, R.id.chat_btn_sendtext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_login_four:
                finish();
                break;
            case chat_btn_sendvoice:

              //  linearAddvoicebutton
                LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(50));
  if(chatEdittext.getVisibility()==View.VISIBLE){

      chatEdittext.setVisibility(View.GONE);
      linearAddvoicebutton.addView(button,params);
  }else{
      linearAddvoicebutton.removeView(button);
     chatEdittext.setVisibility(View.VISIBLE);


  }
                break;
            case R.id.chat_btn_biaoqing:
                chatEdittext.setListener(null);
                setKeyBoardModelPan();
                int tag = (int) chatBtnBiaoqing.getTag();
                if (tag == 1) {
                    // 显示表情
                    buttomLayoutView.setVisibility(View.VISIBLE);
                    chatBtnBiaoqing.setTag(2);
                    hidenKeyBoard(chatEdittext);


                } else {
                    chatBtnBiaoqing.setTag(1);
                    // showKeyBoard(chatEdittext);
                    change();

                }


                break;
            case R.id.chat_btn_jia:


                break;
        }
    }

    public void change() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void OnKeyBoardPop(int keyBoardheight) {


    }
    //dp转px
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {
        //   MyToast.makeText(this,"diangji", Toast.LENGTH_SHORT);
        // handler.sendEmptyMessageDelayed(1,200);


    }


    public void setKeyBoardModelPan() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void setKeyBoardModelResize() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    //隐藏键盘

    public void hidenKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void showKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public void setTextMessage() {

        if (EMClient.getInstance().isConnected()) {

            Toast.makeText(ChatActivity.this, "yilianjie", Toast.LENGTH_SHORT).show();

        }
        EMMessage emMessage = EMMessage.createTxtSendMessage(chatEdittext.getText().toString().trim(), userid);

        EMMessageBody body = emMessage.getBody();
        if (body != null) {


            System.out.println("s = " + body);
            emMessage.setFrom(myid);
            emMessage.setTo(userid);

            EMClient.getInstance().chatManager().sendMessage(emMessage);


            emMessage.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    System.out.println("已发送");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatListView.setSelection(chatList.size() - 1);
                            adapter.notifyDataSetChanged();
                        }
                    });


                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });


        }
    }




    public void receive() {

        //收到消息
//收到透传消息
//收到已读回执
//收到已送达回执
//消息状态变动
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                System.out.println("onMessageReceived messages = " + messages);
                //导入消息导数据库
                EMClient.getInstance().chatManager().importMessages(messages);
                // String s = messages.get(0).getBody().toString();
                if (messages.get(0).getTo().equals(myid) && messages.get(0).getFrom().equals(userid)) {

                    EMMessage.Type type = messages.get(0).getType();
                     if(type.equals(EMMessage.Type.TXT)) {

                         EMTextMessageBody txtBody = (EMTextMessageBody) messages.get(0).getBody();


                         Message message = new Message();
                         message.what = 1;
                         message.obj = txtBody;
                         handler.sendMessage(message);

                     }else if(type.equals(EMMessage.Type.VOICE)){
                             EMVoiceMessageBody voiceMessageBody= (EMVoiceMessageBody) messages.get(0).getBody();
                         Message obtain = Message.obtain();
                            obtain.what=4;
                            obtain.obj=voiceMessageBody;
                           handler.sendMessage(obtain);


                     }


                    EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(userid);
                    emConversation.appendMessage(messages.get(0));
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
                System.out.println("onCmdMessageReceived messages = " + messages);

            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
                System.out.println("onMessageRead messages = " + messages);

            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
                System.out.println("onMessageDelivered messages = " + message);

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
                System.out.println("onMessageChanged messages = " + message);

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        AppManager.getAppManager().finishActivity(this);

    }


    @Override
    public void onBack() {
        chatEdittext.setListener(null);
        System.out.println("chatTitle = onBack");
        setKeyBoardModelResize();
        buttomLayoutView.setVisibility(View.GONE);
        chatBtnBiaoqing.setTag(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("chatTitle = onBack KEYCODE_BACK");
            if (buttomLayoutView.getVisibility() == View.VISIBLE) {
                buttomLayoutView.setVisibility(View.GONE);
                chatBtnBiaoqing.setTag(1);

                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }

        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    //将消息和来自谁添加到map集合中
    protected void addTextToList(String text, int who) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("person", who);
        map.put("image", who == ME ? R.drawable.ic_launcher : R.drawable.ic_launcher);
        map.put("text", text);
        chatList.add(map);
    }

    @Override
    public void onItemClickListener(int position, View view) {

      SpeexPlayer player = new SpeexPlayer(chatList.get(position).get(from[1]).toString(),handler);
        player.startPlay();


    }

    @Override
    public void onItemLongClickListener(int position, View view) {



    }

   /* @OnClick(R.id.voice_btn_chat)
    public void onClick() {
    }*/




    private void initListener() {

        emojiconMenu.setEmojiconMenuListener(new EaseEmojiconMenuBase.EaseEmojiconMenuListener()

        {
            @Override
            public void onExpressionClicked(EaseEmojicon emojicon) {
                if (emojicon.getType() != EaseEmojicon.Type.BIG_EXPRESSION) {
                    if (emojicon.getEmojiText() != null) {
                        chatEdittext.append(EaseSmileUtils.getSmiledText(ChatActivity.this, emojicon.getEmojiText()));
                    }
                }
            }

            @Override
            public void onDeleteImageClicked() {
                if (!TextUtils.isEmpty(chatEdittext.getText())) {
                    KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                    chatEdittext.dispatchKeyEvent(event);
                }
            }
        });
    }


    private void initEmoje(List<EaseEmojiconGroupEntity> emojiconGroupList) {

        if (emojiconMenu == null) {
            emojiconMenu = (EaseEmojiconMenu) View.inflate(ChatActivity.this, com.hyphenate.easeui.R.layout.ease_layout_emojicon_menu, null);
            if (emojiconGroupList == null) {
                emojiconGroupList = new ArrayList<EaseEmojiconGroupEntity>();
                emojiconGroupList.add(new EaseEmojiconGroupEntity(com.hyphenate.easeui.R.drawable.ee_1, Arrays.asList(EaseDefaultEmojiconDatas.getData())));
            }
            ((EaseEmojiconMenu) emojiconMenu).init(emojiconGroupList);
        }
        buttomLayoutView.addView(emojiconMenu);
    }


}
