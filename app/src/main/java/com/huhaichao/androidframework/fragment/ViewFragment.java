package com.huhaichao.androidframework.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseFragment;

/**
 * Created by huhaichao on 2018/3/12.
 */

public class ViewFragment extends BaseFragment {
    public static ViewFragment newInstance() {
        return new ViewFragment();
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
