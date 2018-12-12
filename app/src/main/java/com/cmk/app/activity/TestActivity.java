package com.cmk.app.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmk.app.R;
import com.cmk.app.presenter.TestPresenter;
import com.cmk.app.view.TestView;
import com.cmk.component_base.mvp.base.BaseMvpActivity;

public class TestActivity extends BaseMvpActivity implements TestView, View.OnClickListener {

    private TestPresenter presenter;
    private TextView tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
        initData();
    }

    private void initView() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        tvMsg = findViewById(R.id.tv_message);
        Button btnSucc = findViewById(R.id.btn_succ);
        Button btnFailure = findViewById(R.id.btn_failure);
        Button btnError = findViewById(R.id.btn_error);
        btnError.setOnClickListener(this);
        btnSucc.setOnClickListener(this);
        btnFailure.setOnClickListener(this);
    }


    private void initData() {
        presenter = new TestPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_succ:
              getData();
                break;
            case R.id.btn_failure:
                getDataForFailure();
                break;
            case R.id.btn_error:
                getDataForFailure();
                break;
            default:
                break;
        }
    }

    @Override
    public void showData(String data) {
        tvMsg.setText(data);
    }

    // button 点击事件调用方法
    public void getData() {
        presenter.getData("normal");
    }

    // button 点击事件调用方法
    public void getDataForFailure() {
        presenter.getData("failure");
    }

    // button 点击事件调用方法
    public void getDataForError() {
        presenter.getData("error");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
