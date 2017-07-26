package com.zhd.lenovo.mychat.activirys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.adapters.HorizontalScrollViewAdapter;
import com.zhd.lenovo.mychat.base.AppManager;
import com.zhd.lenovo.mychat.base.BaseMvpActivity;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.DataItemBean;
import com.zhd.lenovo.mychat.bean.ItemBean;
import com.zhd.lenovo.mychat.bean.PhotolistBean;
import com.zhd.lenovo.mychat.daoutils.FirstItemContentDaoUtils;
import com.zhd.lenovo.mychat.daoutils.FirstItemContentPhotoDaoUtils;
import com.zhd.lenovo.mychat.mview.FirstFragmentItemView;
import com.zhd.lenovo.mychat.mview.MonitorScrollView;
import com.zhd.lenovo.mychat.mview.MyHorizontalScrollView;
import com.zhd.lenovo.mychat.presenter.FirstFragmentItemPresenter;
import com.zhd.lenovo.mychat.utils.NetUtil;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;
import com.zhd.lenovo.mychat.widget.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemContentActivity extends BaseMvpActivity<FirstFragmentItemView, FirstFragmentItemPresenter> implements FirstFragmentItemView, MonitorScrollView.OnScrollChangedListener {

    FirstFragmentItemPresenter firstFragmentItemPresenter = new FirstFragmentItemPresenter();
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.sv_translu)
    MonitorScrollView svTranslu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_gallery)
    LinearLayout idGallery;
    @BindView(R.id.id_horizontalScrollView)
    MyHorizontalScrollView idHorizontalScrollView;
    private DataItemBean data;
    private List<PhotolistBean> photolist;
    private HorizontalScrollViewAdapter hadapter;
    private String imgepath;
    private int userid;

    @Override
    public FirstFragmentItemPresenter initPresenter() {

        return firstFragmentItemPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_content);
        ButterKnife.bind(this);


        // StatusBarCompat.compat(this, getResources().getColor(R.color.backgroud));
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);
        imgepath = intent.getStringExtra("imagepath");
        PreferencesUtils.addConfigInfo(IApplication.getApplication(),"imageforother",imgepath);

        Glide.with(this).load(imgepath).into(ivPic);


        firstFragmentItemPresenter.getData(userid);
        initView();
       initListener();
       initmAdapter();
     if(!NetUtil.isNetworkAvailable(IApplication.getApplication())){



      List<PhotolistBean> query = FirstItemContentPhotoDaoUtils.query();
         photolist = FirstItemContentPhotoDaoUtils.query();
//加载数据库中的数据
       hadapter = new HorizontalScrollViewAdapter(this,query);
       idHorizontalScrollView.initDatas(hadapter);
    }

 ivPic.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent intent1=new Intent(ItemContentActivity.this,AllPhotoActivity.class);
     intent1.putExtra("imagephotoview", imgepath);
        startActivity(intent1);


     }
 });
    }

    private void initmAdapter() {


        idHorizontalScrollView.setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener() {
            @Override
            public void onCurrentImgChanged(int position, View viewIndicator) {
          if(photolist!=null&&photolist.size()>position){

       Glide.with(ItemContentActivity.this).load(photolist.get(position).getImagePath()).into(ivPic);

          }

            }
        });
//添加点击回调
        idHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
              if(photolist!=null&&photolist.size()>pos){
           imgepath=photolist.get(pos).getImagePath();
                    Glide.with(ItemContentActivity.this).load(photolist.get(pos).getImagePath()).into(ivPic);

                }
            }
        });

    }


    private void initListener() {
        svTranslu.setOnScrollChangedListener(this);
    }

    private void initView() {

        llContent = (LinearLayout) findViewById(R.id.ll_content);
        svTranslu = (MonitorScrollView) findViewById(R.id.sv_translu);
        ivPic = (ImageView) findViewById(R.id.iv_pic);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setTitle(data.getNickname());
        toolbar.getBackground().setAlpha(0);  //先设置透明
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设为 false
//           actionBar.setDisplayShowTitleEnabled(false);  //是否隐藏标题
            actionBar.setDisplayHomeAsUpEnabled(true);     //是否显示返回按钮
        }

        //实现透明状态栏效果  并且toolbar 需要设置  android:fitsSystemWindows="true"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);


        }
        View view = View.inflate(this, R.layout.layout_item_top_ziliao, null);
        llContent.addView(view);
    }


    //dp转px
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }


    @Override
    public void success(ItemBean itemBean) {

        Toast.makeText(IApplication.getApplication(), itemBean.getResult_message(), Toast.LENGTH_SHORT).show();
        data = itemBean.getData();

        photolist = data.getPhotolist();
        FirstItemContentDaoUtils.delete();
        List<DataItemBean>list=new ArrayList<>();
           list.add(data);
        FirstItemContentPhotoDaoUtils.insert(photolist);
        FirstItemContentDaoUtils.insert(list);
       addconView();

        toolbar.setTitle(data.getNickname());

         FirstItemContentPhotoDaoUtils.delete();



       hadapter = new HorizontalScrollViewAdapter(this,photolist);
       idHorizontalScrollView.initDatas(hadapter);

    }

    private void addconView() {
        if (data != null) {

            for (int i = 0; i < 5; i++) {
                Button button=null;
                TextView textView = new TextView(this);

                switch (i) {


                    case 0:
                        textView.setText( "   昵称：  "+data.getNickname());

                        break;

                    case 1:
                        textView.setText("   所在地： " + data.getArea());


                        break;
                    case 2:
                        textView.setText("   性别： " + data.getGender());

                        break;
                    case 3:
                        textView.setText("   简介： " + data.getIntroduce());
                        break;
                    case 4:
                       button=new Button(this);
                        button.setText("打招呼");

                        break;
                }


                textView.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(60));

                llContent.addView(textView, params);
                if(button!=null){
                 //添加好友
                    button.setGravity(Gravity.CENTER);
                    button.setPadding(5,5,5,5);
                    button.setBackgroundColor( getResources().getColor(R.color.backgroud));
                    llContent.addView(button);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
           if(data.getRelation()==0){
               Intent intent=new Intent(ItemContentActivity.this,AddFriendActivity.class);
                 intent.putExtra("friendId",userid+"");
                intent.putExtra("nickname",data.getNickname());
                startActivity(intent);


           }else{
              Intent intent=new Intent(ItemContentActivity.this,ChatActivity.class);
               intent.putExtra("userid",userid+"");
               startActivity(intent);

               MyToast.makeText(IApplication.getApplication(),"跳转到聊天页面",Toast.LENGTH_SHORT);
           }


             }
         });

                }
            }
        }

    }

    @Override
    public void failed(int code) {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toolbar上的左上角的返回箭头的键值为Android.R.id.home  不是R.id.home
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        float height = ivPic.getHeight();  //获取图片的高度
        if (oldt < height) {
            int i = Float.valueOf(oldt / height * 255).intValue();    //i 有可能小于 0
            toolbar.getBackground().setAlpha(Math.max(i, 0));   // 0~255 透明度
        } else {
            toolbar.getBackground().setAlpha(255);
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        toolbar.setBackgroundResource(R.color.backgroud);

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager appManager = AppManager.getAppManager();
        appManager.finishActivity(this);

    }
}
