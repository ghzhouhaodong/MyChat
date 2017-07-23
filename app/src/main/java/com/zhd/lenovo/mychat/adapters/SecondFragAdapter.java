package com.zhd.lenovo.mychat.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.bean.FriendDataBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/7/15.
 */

public class SecondFragAdapter extends BaseAdapter {
    private Context context;
    private List<FriendDataBean> listbean;

    public SecondFragAdapter(Context context, List<FriendDataBean> listbean) {
        this.context = context;
        this.listbean = listbean;
    }

    @Override
    public int getCount() {
        return listbean.size();
    }

    @Override
    public Object getItem(int position) {
        return listbean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      MyViewHolder holder;

        if (convertView == null) {
            holder=new MyViewHolder();
            convertView = View.inflate(context, R.layout.friend_layout_linear, null);
            ButterKnife.bind(this, convertView);
       holder.friendListAge= (TextView) convertView.findViewById(R.id.friend_list_age);
        holder.friendLinearBtn=(LinearLayout)convertView.findViewById(R.id.friend_linear_btn);
        holder.friendListDoc= (TextView) convertView.findViewById(R.id.friend_list_doc);
      holder.friendListImage = (SimpleDraweeView) convertView.findViewById(R.id.friend_list_image);
            holder.friendListPlace= (TextView) convertView.findViewById(R.id.friend_list_place);
              holder.friendListPhonenum= (TextView) convertView.findViewById(R.id.friend_list_phonenum);
             holder.friendListName= (TextView) convertView.findViewById(R.id.friend_list_name);
         convertView.setTag(holder);


        }else{
          holder= (MyViewHolder) convertView.getTag();

        }


   holder.friendListAge.setText(listbean.get(position).getAge());
   holder.friendListName.setText(listbean.get(position).getNickname());
   holder.friendListDoc.setText(listbean.get(position).getIntroduce());
     holder.friendListPlace.setText(listbean.get(position).getArea());
     holder.friendListPhonenum.setText(listbean.get(position).getPhone());
          //  Glide.with(context).load(listbean.get(position).getImagePath()).into(holder.friendListImage);

        holder.friendListImage.setImageURI(listbean.get(position).getImagePath());



        return convertView;


    }

    class MyViewHolder {

        @BindView(R.id.friend_list_image)
        SimpleDraweeView friendListImage;
        @BindView(R.id.friend_list_place)
        TextView friendListPlace;
        @BindView(R.id.friend_list_name)
        TextView friendListName;
        @BindView(R.id.friend_list_age)
        TextView friendListAge;
        @BindView(R.id.friend_list_phonenum)
        TextView friendListPhonenum;
        @BindView(R.id.friend_list_doc)
        TextView friendListDoc;
        @BindView(R.id.friend_linear_btn)
        LinearLayout friendLinearBtn;

    }



}
