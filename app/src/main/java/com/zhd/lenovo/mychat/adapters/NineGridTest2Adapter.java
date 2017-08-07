package com.zhd.lenovo.mychat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.bean.FriendsQuanBean;
import com.zhd.lenovo.mychat.model.NineGridTestModel;
import com.zhd.lenovo.mychat.mview.NineGridTestLayout;

import java.util.List;

/**
 * Created by HMY on 2016/8/6
 */
public class NineGridTest2Adapter extends RecyclerView.Adapter<NineGridTest2Adapter.ViewHolder> {

    private Context mContext;
    private List<FriendsQuanBean.ListBean> mList;
    protected LayoutInflater inflater;

    public NineGridTest2Adapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<FriendsQuanBean.ListBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.item_bbs_nine_grid, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(mList.get(position).getType()==1){

        }else if(mList.get(position).getType()==2){
          String pic = mList.get(position).getPic();


       String[] split = pic.split("\\|");

    NineGridTestModel   model=new NineGridTestModel();
          for(int x=0; x< split.length; x++){
                model.urlList.add(split[x]);
            }
        holder.layout.setIsShowAll(false);
        holder.layout.setUrlList(model.urlList);
        }
    if(mList.get(position).getContent().length()<120){
        holder.textView.setText(mList.get(position).getContent());
    }else {

     holder.textView.setText(mList.get(position).getContent().substring(0,120)+ "..");
     holder.textViewmore.setVisibility(View.VISIBLE);
    }


    }

    @Override
    public int getItemCount() {
        return getListSize(mList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridTestLayout layout;
        TextView textView;
        TextView textViewmore;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
              textView= (TextView) itemView.findViewById(R.id.thired_friends_content);
            textViewmore= (TextView) itemView.findViewById(R.id.thired_friend_more);

        }
    }

    private int getListSize(List<FriendsQuanBean.ListBean> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
}
