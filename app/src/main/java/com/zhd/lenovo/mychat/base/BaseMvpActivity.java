package com.zhd.lenovo.mychat.base;

import android.os.Bundle;

import com.zhd.lenovo.mychat.R;

public abstract class BaseMvpActivity<V,T extends BasePresenter<V>> extends IActivity {
 public T presenter;
   public abstract T initPresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_mvp);

      presenter =initPresenter();

    }

    @Override
    protected void onResume() {
        super.onResume();
      presenter.attach((V)this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    presenter.detach();


    }
}
