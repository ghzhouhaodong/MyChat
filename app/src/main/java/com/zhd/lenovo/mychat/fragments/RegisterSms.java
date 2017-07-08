package com.zhd.lenovo.mychat.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.activirys.RegisterActivity;
import com.zhd.lenovo.mychat.base.BaseFragment;
import com.zhd.lenovo.mychat.base.IApplication;
import com.zhd.lenovo.mychat.mview.RegisterSmsView;
import com.zhd.lenovo.mychat.presenter.RegisterSmsPresenter;
import com.zhd.lenovo.mychat.widget.MyToast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterSms extends BaseFragment<RegisterSmsView, RegisterSmsPresenter> implements RegisterSmsView {
    InputMethodManager imm;
    EventHandler eventHandler;
    @BindView(R.id.back_login_two)
    ImageView backLoginTwo;
    @BindView(R.id.phone_num)
    EditText phoneNum;
    @BindView(R.id.phone_yanzheng)
    EditText phoneYanzheng;
    @BindView(R.id.yanzheng_btn)
    Button yanzhengBtn;
    @BindView(R.id.register_sms_btn)
    Button registerSmsBtn;
    Unbinder unbinder;
    private RegisterActivity registerActivity;

    @Override
    public RegisterSmsPresenter initPresenter() {
        return new RegisterSmsPresenter();
    }

    public RegisterSms() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_sms, container, false);
        registerActivity = (RegisterActivity) getActivity();

        SMSSDK.initSDK(getActivity(), "1f32fed5ada85", "7495edc00c046289be1fae0842775f0f");
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                System.out.println("result = " + result);
                System.out.println("data = " + data);

            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        unbinder = ButterKnife.bind(this, view);
        return view;


    }

    @Override
    public void phoneError(int type) {
        switch (type){
            case 1:
                MyToast.makeText(IApplication.getApplication(),"手机号码不能为空", Toast.LENGTH_SHORT);

                break;
            case 2:
                MyToast.makeText(IApplication.getApplication(),"手机格式不正确",Toast.LENGTH_SHORT);
                break;
        }

    }
    private Disposable disposable ;
    /**
     * 显示倒计时
     */
    @Override
    public void showTimer() {

        yanzhengBtn.setClickable(false);

        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(30)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return 29 - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


                        disposable = d ;
//                        d.dispose();

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {

                        System.out.println("aLong = " + aLong);
                        yanzhengBtn.setText(aLong+" s ");

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        yanzhengBtn.setClickable(true);
                        yanzhengBtn.setText(getText(R.string.register_sms));

                    }
                });




    }

    @Override
    public void toNextPage() {
       registerActivity.toPos(1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.phone_num, R.id.phone_yanzheng, R.id.yanzheng_btn, R.id.register_sms_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_num:
                break;
            case R.id.phone_yanzheng:
                break;
            case R.id.yanzheng_btn:
                presenter.getVerificationCode(phoneNum.getText().toString().trim());


                break;
            case R.id.register_sms_btn:

   registerActivity.setPhone(phoneNum.getText().toString().trim());
    presenter.nextStep(phoneNum.getText().toString().trim(),phoneYanzheng.getText().toString().trim());
                imm.hideSoftInputFromWindow(phoneYanzheng.getWindowToken(), 0);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     if(disposable!=null) {
        disposable.dispose();
     }
     }


}
