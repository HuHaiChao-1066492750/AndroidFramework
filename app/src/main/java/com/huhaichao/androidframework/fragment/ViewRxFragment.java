package com.huhaichao.androidframework.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseRxFragment;

import butterknife.BindView;

/**
 * Created by huhaichao on 2018/3/12.
 */

public class ViewRxFragment extends BaseRxFragment {

    public static ViewRxFragment newInstance() {
        return new ViewRxFragment();
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_view;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {

    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {

    }
}
