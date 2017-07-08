package com.zhd.lenovo.mychat.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.lljjcoder.citypickerview.widget.CityPickerView;
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.activirys.RegisterActivity;
import com.zhd.lenovo.mychat.activirys.UploadPhotoActivity;
import com.zhd.lenovo.mychat.base.AppManager;
import com.zhd.lenovo.mychat.base.BaseFragment;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.bean.RegisterBean;
import com.zhd.lenovo.mychat.cipher.Md5Utils;
import com.zhd.lenovo.mychat.mview.RegisterInfoView;
import com.zhd.lenovo.mychat.presenter.RegisterInfoPresenter;
import com.zhd.lenovo.mychat.widget.MyToast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterInfoFragment extends BaseFragment<RegisterInfoView, RegisterInfoPresenter> implements RegisterInfoView {


    @BindView(R.id.back_login_three)
    ImageView backLoginThree;
    @BindView(R.id.registerUsername)
    EditText registerUsername;
    @BindView(R.id.username_click)
    LinearLayout usernameClick;
    @BindView(R.id.register_Sex)
  TextView registerSex;
    @BindView(R.id.sex_click)
    LinearLayout sexClick;
    @BindView(R.id.register_age)
     TextView registerAge;
    @BindView(R.id.age_click)
    LinearLayout ageClick;
    @BindView(R.id.register_diqv)
    TextView registerDiqv;
    @BindView(R.id.diqv_click)
    LinearLayout diqvClick;
    @BindView(R.id.register_jianjie)
    TextView registerJianjie;
    @BindView(R.id.jianjie_click)
    LinearLayout jianjieClick;
    @BindView(R.id.register_next)
    Button registerNext;
    Unbinder unbinder;
    @BindView(R.id.registerUserpwd)
    EditText registerUserpwd;
    private CityPickerView cityPickerView;

    @Override
    public RegisterInfoPresenter initPresenter() {
        return new RegisterInfoPresenter();
    }

    public RegisterInfoFragment() {
        // Required empty public constructor
    }

    RegisterActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_info, container, false);
        activity = (RegisterActivity) getActivity();

        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void registerSuccess(RegisterBean registerBean) {
if(registerBean.getResult_code()==200){
    activity.toIActivity(UploadPhotoActivity.class,null,0);
          getActivity().finish();
    AppManager.getAppManager().finishActivity(getActivity());
       // AppManager.getAppManager().finishActivity(LoginActivity.class);


}else{

   MyToast.makeText(IApplication.getApplication(),registerBean.getResult_message(),Toast.LENGTH_SHORT);

}

    }

    @Override
    public void registerFailed(int code) {
        MyToast.makeText(IApplication.getApplication(),code+"", Toast.LENGTH_SHORT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.username_click, R.id.sex_click, R.id.age_click, R.id.diqv_click, R.id.jianjie_click, R.id.register_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.username_click:


                break;
            case R.id.sex_click:
                showSexChooseDialog();
                break;
            case R.id.age_click:
                showAgeDialog();

                break;
            case R.id.diqv_click:
                showCity();


                break;
            case R.id.jianjie_click:
                activity.toPos(2);

                break;
            case R.id.register_next:
              toData();



                break;
        }
    }

    private void toData() {
        RxView.clicks(registerNext).throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {

                        presenter.vaildInfor(phone, registerUsername.getText().toString().trim(), registerSex.getText().toString().trim()
                                , registerAge.getText().toString().trim(), registerDiqv.getText().toString().trim()
                                , registerJianjie.getText().toString().trim(), Md5Utils.getMD5(registerUserpwd.getText().toString().trim()));
                    }
                });

    }

    private void showCity() {
        cityPickerView = new CityPickerView(getActivity());
        cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                String province = citySelected[0];
                String city = citySelected[1];
                String district = citySelected[2];
                String citycont = province + city + district;
                registerDiqv.setText(citycont);

            }
        });
        cityPickerView.show();

    }

    //年龄
    private String[] sexArry = new String[]{"女", "男"};

    private void showSexChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择性别");
        builder.setItems(sexArry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                registerSex.setText(sexArry[which]);
            }
        });
        builder.show();
    }

    //年龄
    AlertDialog.Builder builder;

    private void showAgeDialog() {
        if (builder == null) {
            final String[] ages = getResources().getStringArray(R.array.age);
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请选择年龄");
            builder.setItems(ages, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    registerAge.setText(ages[which]);
                }
            });
        }

        builder.show();

    }

    public void setDes(String des) {
        if (!TextUtils.isEmpty(des)) {
            registerJianjie.setText(des);

        }
    }
  private  String phone;
  public void setPhone(String phone){

      this.phone=phone;

  }





}
