package com.huhaichao.androidframework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseRxActivity;

import butterknife.BindView;

public class BitmapRxActivity extends BaseRxActivity {
    @BindView(R.id.activity_bitmap_bt_1)
    protected Button activity_bitmap_bt_1;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bitmap;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        activity_bitmap_bt_1.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {


    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.activity_bitmap_bt_1:
                startActivity(new Intent(this, MessageRxActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {

    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {

    }
}
