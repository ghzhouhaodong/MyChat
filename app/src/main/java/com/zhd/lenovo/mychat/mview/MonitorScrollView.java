package com.zhd.lenovo.mychat.mview;
 
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MonitorScrollView extends ScrollView {

    private OnScrollChangedListener mOnScrollListener;
 
    public MonitorScrollView(Context context) {
        super(context);
    }
 
    public MonitorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public MonitorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
 
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
 
    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollListener = listener;
    }
 
    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }
}