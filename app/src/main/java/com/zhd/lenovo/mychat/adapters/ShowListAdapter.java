package com.zhd.lenovo.mychat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.bean.DisplayBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/8/8.
 */

public class ShowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<DisplayBean.ListBean> list;


    public ShowListAdapter(Context context, List<DisplayBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_showlist, null);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
          //id
        myViewHolder.userid.setText(list.get(position).getId()+"");
        Glide.with(context).load(list.get(position).getLivepic()).into(myViewHolder.imageShowlist);
        myViewHolder.imageShowlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(position,v);


            }
        });


    }

    @Override
    public int getItemCount() {


        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_showlist)
        ImageView imageShowlist;
        @BindView(R.id.userid_showlist)
        TextView userid;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
     public  interface OnItemClickListener {
        void onItemClickListener(int position, View view);
        void onItemLongClickListener(int position, View view);
    }
    public OnItemClickListener listener ;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


}
