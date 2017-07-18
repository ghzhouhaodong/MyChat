package com.zhd.lenovo.mychat.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.bumptech.glide.Glide;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.activirys.ItemContentActivity;
import com.zhd.lenovo.mychat.bean.DataBean;
import com.zhd.lenovo.mychat.bean.PhotoBean;
import com.zhd.lenovo.mychat.utils.AMapUtils;
import com.zhd.lenovo.mychat.utils.DeviceUtils;
import com.zhd.lenovo.mychat.utils.DisanceUtils;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.inflate;

/**
 * Created by lenovo on 2017/7/11.
 */

public class FirstFragAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<DataBean> list= new ArrayList<DataBean>();
    private Context context;
    private int tag = 1;
    private int itemWidth;

    public FirstFragAdapter(Context context) {
        this.context = context;
        itemWidth = DeviceUtils.getDisplayInfomation(context).x / 3;

    }

    public void setData(PhotoBean data, String page) {
        if (list == null) {
            list = new ArrayList<DataBean>();

        }
        if (page.equals("1")) {
            list.clear();

        }


        list.addAll(data.getData());
        notifyDataSetChanged();


    }
  public void setData(List<DataBean>list){
      this.list.addAll(list);


  }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = inflate(context, R.layout.layout_waterfall, null);
            PinterestViewHolder pinterestViewHolder = new PinterestViewHolder(view);

            return pinterestViewHolder;
        } else if(viewType ==0){
            View view = View.inflate(context, R.layout.layout_list, null);
            VerticalviewHolder verticalviewHolder = new VerticalviewHolder(view);

            return verticalviewHolder;

        }

      return  null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof VerticalviewHolder) {

            //列表的形式展示
            VerticalviewHolder verticalViewHolder = (VerticalviewHolder) holder;

            verticalViewHolder.listName.setText(list.get(position).getNickname());
            verticalViewHolder.listPlace.setText(list.get(position).getArea());

            verticalViewHolder.listdoc.setText(list.get(position).getIntroduce());
            verticalViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               //跳转到详情
                    int userId = list.get(position).getUserId();
                    String imagePath = list.get(position).getImagePath();
                    Intent intent=new Intent(context, ItemContentActivity.class);
           intent.putExtra("userid", userId);
            intent.putExtra("imagepath",imagePath);
                 context.startActivity(intent);


                }
            });
            Glide.with(context).load(list.get(position).getImagePath()).into(verticalViewHolder.listImage);

            String lat =  PreferencesUtils.getValueByKey(context, AMapUtils.LAT,"");
            String lng = PreferencesUtils.getValueByKey(context,AMapUtils.LNG,"");

            double olat = list.get(position).getLat();
            double olng = list.get(position).getLng() ;


            if(!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && olat != 0.0 && olng != 0.0){

                double dlat = Double.valueOf(lat);
                double dlng = Double.valueOf(lng);
                DPoint dPoint = new DPoint(dlat,dlng);
                DPoint oPoint = new DPoint(olat,olng);

                //计算两点之间的距离
                float dis =  CoordinateConverter.calculateLineDistance(dPoint,oPoint);

                verticalViewHolder.listage.setText(list.get(position).getAge() + "岁 ,  " + list.get(position).getGender() + " ,  " + DisanceUtils.standedDistance(dis));

            } else {

                verticalViewHolder.listage.setText(list.get(position).getAge() + "岁 ,  " + list.get(position).getGender());

            }
        } else if(holder instanceof PinterestViewHolder){
            PinterestViewHolder staggeredViewHolder = (PinterestViewHolder) holder;

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) staggeredViewHolder.waterfallImage.getLayoutParams() ;

            float scale =  (float) itemWidth / (float) list.get(position).getPicWidth()  ;
            params.width = itemWidth;
            params.height = (int)( (float)scale * (float)list.get(position).getPicHeight()) ;

            System.out.println("params.scale = " + scale);
            System.out.println("params.width = " + params.width + " " + list.get(position).getPicWidth());
            System.out.println("params.height = " + params.height + "  " + list.get(position).getPicHeight());

            staggeredViewHolder.waterfallImage.setLayoutParams(params);

//            params.width = itemWidth


            Glide.with(context).load(list.get(position).getImagePath()).into(staggeredViewHolder.waterfallImage);
            String lat =  PreferencesUtils.getValueByKey(context, AMapUtils.LAT,"");
            String lng = PreferencesUtils.getValueByKey(context,AMapUtils.LNG,"");

            double olat = list.get(position).getLat();
            double olng = list.get(position).getLng() ;


            if(!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && olat != 0.0 && olng != 0.0){

                double dlat = Double.valueOf(lat);
                double dlng = Double.valueOf(lng);
                DPoint dPoint = new DPoint(dlat,dlng);
                DPoint oPoint = new DPoint(olat,olng);

                //计算两点之间的距离
                float dis =  CoordinateConverter.calculateLineDistance(dPoint,oPoint);

               staggeredViewHolder.waterfalljvli.setText(list.get(position).getAge() + "岁 ,  " + list.get(position).getGender() + " ,  " + DisanceUtils.standedDistance(dis));
               /* Shader shader =new LinearGradient(0, 0, 0, 20, Color.WHITE, Color.GRAY, Shader.TileMode.CLAMP);
                staggeredViewHolder.waterfalljvli.getPaint().setShader(shader);*/
            } else {

                staggeredViewHolder.waterfalljvli.setText(list.get(position).getAge() + "岁 ,  " + list.get(position).getGender());

            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (tag == 1) {
            return 0;
        } else {
            return 1;
        }
    }


    public void dataChange(int type) {

        this.tag = type;

    }



    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }


    static class PinterestViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.waterfall_image)
        ImageView waterfallImage;
        @BindView(R.id.waterfall_jvli)
        TextView waterfalljvli;
         /* @BindView(R.id.waterfall_age)
          TextView waterfallage;*/
        public PinterestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    static class VerticalviewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_image)
        ImageView listImage;
        @BindView(R.id.list_place)
        TextView listPlace;
        @BindView(R.id.list_name)
        TextView listName;
        @BindView(R.id.list_age)
        TextView listage;
        @BindView(R.id.list_doc)
         TextView listdoc;
        @BindView(R.id.linear_goto_content)
          LinearLayout linearLayout;
        public VerticalviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
