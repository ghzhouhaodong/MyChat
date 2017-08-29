package com.zhd.lenovo.mychat.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhd.lenovo.mychat.R;

import java.util.List;

/**
 * Created by lenovo on 2017/8/8.
 */

public class ZBChatcontentAdapter extends BaseAdapter {
      public Context context;
    public List<String> list;

    public ZBChatcontentAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
       if(convertView==null){
           holder=new MyViewHolder();
             convertView= View.inflate(context, R.layout.item_chatlist_zb,null);
            holder.textView= (TextView) convertView.findViewById(R.id.textview_item_zb);

           convertView.setTag(holder);

       }else{
           holder= (MyViewHolder) convertView.getTag();

       }
       holder.textView.setText(list.get(position));



        return convertView;
    }
      class MyViewHolder{
          TextView textView;



      }



}
