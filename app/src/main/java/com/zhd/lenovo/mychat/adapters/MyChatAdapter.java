package com.zhd.lenovo.mychat.adapters;

import android.content.Context;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.zhd.lenovo.mychat.base.IApplication;

import java.util.ArrayList;
import java.util.HashMap;

public  class MyChatAdapter extends BaseAdapter {
    public final static int OTHER = 1;
    public final static int ME = 0;

        Context context = null;
        ArrayList<HashMap<String, Object>> chatList = null;
        int[] layout;
        String[] from;
        int[] to;


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


            return super.getItemViewType(position);
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
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

            holder.imageView.setBackgroundResource((Integer) chatList.get(position).get(from[0]));
            if(chatList.get(position).get(from[1]).toString().endsWith(".spx")){



                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(position,v);

                    }
                });
            }else{
                Spannable span = EaseSmileUtils.getSmiledText(IApplication.application, chatList.get(position).get(from[1]).toString());

                holder.textView.setText(span, TextView.BufferType.SPANNABLE);
            }





            return convertView;
        }


        //接口回调
     public   interface OnItemClickListener {
            void onItemClickListener(int position, View view);
            void onItemLongClickListener(int position, View view);
        }
        public OnItemClickListener listener ;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }



    }
