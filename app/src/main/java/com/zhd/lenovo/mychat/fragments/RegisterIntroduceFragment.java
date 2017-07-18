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
import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.activirys.RegisterActivity;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterIntroduceFragment extends Fragment {
    @BindView(R.id.back_login_two)
    ImageView backLoginTwo;
    @BindView(R.id.jianjie_context)
    EditText jianjieContext;
    @BindView(R.id.save_jianjie)
    Button saveJianjie;
    Unbinder unbinder;
    private InputMethodManager imm;
    private RegisterActivity registerActivity;
    public RegisterIntroduceFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_introduce, container, false);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        registerActivity = (RegisterActivity) getActivity();

        unbinder = ButterKnife.bind(this, view);
        jianjieContext.requestFocus();
        //跳转新的Activity自动弹出软件盘
        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
                       {
                           public void run()
                           {
                               InputMethodManager inputManager =
                                       (InputMethodManager)jianjieContext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(jianjieContext, 0);
                        timer.cancel();

                           }
                       },
                500);
        //强制弹出软
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @OnClick(R.id.save_jianjie)
    public void onClick() {
        //强制隐藏键盘
        imm.hideSoftInputFromWindow(jianjieContext.getWindowToken(), 0);
        String des=    jianjieContext.getText().toString().trim();
        registerActivity.setDes(des);
        registerActivity.toPos(1);
    }
}
