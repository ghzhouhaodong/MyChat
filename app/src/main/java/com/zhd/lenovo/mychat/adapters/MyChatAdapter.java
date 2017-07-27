package com.zhd.lenovo.mychat.adapters;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.base.IApplication;

import java.util.ArrayList;
import java.util.HashMap;


public  class MyChatAdapter extends BaseAdapter {
    public final static int OTHER = 1;
    public final static int ME = 0;
    public static final int VALUE_TXT = 0;
    public static final int VALUE_VOICE = 1;
    Context context = null;
        ArrayList<HashMap<String, Object>> chatList = null;
        int[] layout;
        String[] from;
        int[] to;
    int [] tovoice={R.id.chatlist_voicehead_me,R.id.chatlist_voice_time_me,R.id.chatlist_voicehead__other,R.id.chatlist_voice_time_other};
     int [] todonghua={R.id.chatlist_voice_me,R.id.chatlist_voice_other};
    int[] layoutvoice = {R.layout.chatlist_voice_for_me,R.layout.chatlist_voice_for_you};
    public MyChatAdapter(Context context,
                             ArrayList<HashMap<String, Object>> chatList, int[] layout,
                             String[] from, int[] to) {
            super();
            this.context = context;
            this.chatList = chatList;
            this.layout = layout;
            this.from = from;
            this.to = to;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return chatList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return chatList.get(arg0);


        }

        @Override
        public int getItemViewType(int position) {
   if(chatList.get(position).get("flag").equals("1")){
      return VALUE_TXT;

   }else if(chatList.get(position).get("flag").equals("2")){

       return VALUE_VOICE;
   }
        return 0;

        }
 //返回两种类型
    @Override
    public int getViewTypeCount() {


        return 2;
    }

    @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        class ViewHolder {
            public ImageView imageView = null;
            public TextView textView = null;

        }
       class VoiceViewHolder{
         public ImageView imageHead =null;
        public  ImageView imagedonghua=null;
        public TextView  time=null;

       }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            int type = getItemViewType(position);
        switch (type) {
            case VALUE_TXT:
                ViewHolder holder = null;
                //获得到消息是谁发的
                int who = (Integer) chatList.get(position).get("person");
                //加载who的布局
                convertView = LayoutInflater.from(context).inflate(
                        layout[who == ME ? 0 : 1], null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(to[who * 2 + 0]);
                holder.textView = (TextView) convertView.findViewById(to[who * 2 + 1]);
                holder.textView.setText("语音");

                Glide.with(context).load(chatList.get(position).get(from[0])).into(holder.imageView);
                // holder.imageView.setBackgroundResource((Integer) chatList.get(position).get(from[0]));
                if(chatList.get(position).get(from[1]).toString().endsWith(".spx")){



                    holder.textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                      //      listener.onItemClickListener(position,v);

                        }
                    });
                }else{
                    Spannable span = EaseSmileUtils.getSmiledText(IApplication.application, chatList.get(position).get(from[1]).toString());

                    holder.textView.setText(span, TextView.BufferType.SPANNABLE);
                }








                break;

            case VALUE_VOICE:
               VoiceViewHolder  holder2 = null;
                //获得到消息是谁发的
                int who2 = (Integer) chatList.get(position).get("person");
                //加载who的布局
                convertView = LayoutInflater.from(context).inflate(
                        layoutvoice[who2 == ME ? 0 : 1], null);
                holder2 = new VoiceViewHolder();
                holder2.imageHead = (ImageView) convertView.findViewById(tovoice[who2 * 2 + 0]);
                holder2.time = (TextView) convertView.findViewById(tovoice[who2 * 2 + 1]);
                holder2.imagedonghua= (ImageView) convertView.findViewById(todonghua[who2]);
                holder2.time.setText((String)chatList.get(position).get("voicetime")+"''");
                Glide.with(context).load((String) chatList.get(position).get(from[0])).into(holder2.imageHead);
              //  voicetext
                 //  chatList.get(position).get("voicetext");
                final AnimationDrawable animationDrawable;
              if(who2==ME){


                  holder2.imagedonghua.setImageResource(R.drawable.voice_to_icon);
                  animationDrawable = (AnimationDrawable) holder2.imagedonghua.getDrawable();

              }else{


                  holder2.imagedonghua.setImageResource(R.drawable.voice_from_icon);
                  animationDrawable = (AnimationDrawable) holder2.imagedonghua.getDrawable();

              }

                holder2.imagedonghua.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                        listener.onItemClickListener(position,v,(String)chatList.get(position).get("voicetime"), animationDrawable);


                    }
               });






                break;
        }


            return convertView;
        }


        //接口回调
     public   interface OnItemClickListener {
            void onItemClickListener(int position, View view,String time,AnimationDrawable animationDrawable);
            void onItemLongClickListener(int position, View view);
        }
        public OnItemClickListener listener ;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }



    }
