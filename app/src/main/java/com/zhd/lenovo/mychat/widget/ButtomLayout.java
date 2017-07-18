package com.zhd.lenovo.mychat.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zhd.lenovo.mychat.R;
import com.zhd.lenovo.mychat.activirys.LoginActivity;
import com.zhd.lenovo.mychat.utils.PreferencesUtils;


/**
 * Created by lenove on 17/7/9.
 */

public class ButtomLayout extends LinearLayout {


    public ButtomLayout(Context context) {
        this(context,null);
    }

    public ButtomLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ButtomLayout(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);






        View view =  LayoutInflater.from(context).inflate(R.layout.buttom_tab,this);

        RadioGroup radioGroup = (RadioGroup)  view.findViewById(R.id.tab_radiogroup);

        RadioButton radioButtonFirst = (RadioButton) view.findViewById(R.id.radiobutton_home);
        RadioButton radioButtonSecond = (RadioButton) view.findViewById(R.id.radiobutton_discover);
        RadioButton radioButtonThird = (RadioButton) view.findViewById(R.id.radiobutton_feed);
        RadioButton radioButtonFourth = (RadioButton) view.findViewById(R.id.radiobutton_me);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,  int checkedId) {


                switch (checkedId) {

                    case R.id.radiobutton_home:

                        setListener(0);
                        break;

                    case R.id.radiobutton_discover:
                        setListener(1);
                        Boolean islogin = PreferencesUtils.getValueByKey(context, "islogin", false);
                        if(!islogin){
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }



                        break;

                    case R.id.radiobutton_feed:
                        setListener(2);

                        break;

                    case R.id.radiobutton_me:
                        setListener(3);


                        break;
                }

            }
        });


    }





    public void setListener(int index){

        if(listener != null){
            listener.onSelect(index);
        }
    }



    public interface OnSelectListener {
        public void onSelect(int index);
    }

    public OnSelectListener listener ;

    public void setOnSelectListener(OnSelectListener listener){
        this.listener = listener ;
    }








}
