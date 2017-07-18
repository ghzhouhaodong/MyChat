package com.zhd.lenovo.mychat.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.bean.PhotolistBean;

import java.util.List;

public class HorizontalScrollViewAdapter
{

	private Context mContext;
	private LayoutInflater mInflater;
	private List<PhotolistBean> mDatas;

	public HorizontalScrollViewAdapter(Context context, List<PhotolistBean> mDatas)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	public int getCount()
	{

		return mDatas==null?0:mDatas.size();
	}

	public Object getItem(int position)
	{
		return mDatas.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_index_gallery_item, parent, false);
			viewHolder.mImg = (ImageView) convertView
					.findViewById(R.id.id_index_gallery_item_image);
			viewHolder.mText = (TextView) convertView
					.findViewById(R.id.id_index_gallery_item_text);

			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//加载图片

		if(mDatas.size()>(position)){
			Log.e("position",""+position);
    Glide.with(mContext).load(mDatas.get(position).getImagePath())


			//.submit(mDatas.get(position).getPicWidth(),mDatas.get(position).getPicHeight());
			.into(viewHolder.mImg);


			viewHolder.mText.setText("");


		}else{
		//	MyToast.makeText(IApplication.getApplication(),"Ta还没有相册", Toast.LENGTH_SHORT);
		}

		return convertView;
	}

	private class ViewHolder
	{
		ImageView mImg;
		TextView mText;
	}

}
