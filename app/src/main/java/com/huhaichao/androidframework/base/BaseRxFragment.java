package com.huhaichao.androidframework.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.huhaichao.androidframework.R;
import com.huhaichao.framework.network.retrofit.HttpRequestCallback;

import butterknife.ButterKnife;

/**
 * Created by HuHaiChao on 2018/6/1.
 */

public abstract class BaseRxFragment extends com.huhaichao.framework.base.BaseRxFragment implements HttpRequestCallback<JSONObject> {
    public static final String TAG = "BaseRxFragment";
    protected View mContentView;
    protected Activity mActivity;
    private long lastClick = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setBaseView(inflater, bindLayout());
        ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    protected void setBaseView(@NonNull LayoutInflater inflater, @LayoutRes int layoutId) {
        if (layoutId <= 0) {
            return;
        }
        mContentView = inflater.inflate(layoutId, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        initData(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        initView(savedInstanceState, mContentView);
        doBusiness();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        if (mContentView != null) {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) {
            onWidgetClick(view);
        }
    }

    public void Toast(String msg) {
        resetToast();
        ToastUtils.showShort(msg);
    }

    private void resetToast() {
        ToastUtils.setMsgColor(0xFEFFFFFF);
        ToastUtils.setBgColor(0xFEFFFFFF);
        ToastUtils.setBgResource(-1);
        ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, getResources().getDimensionPixelSize(R.dimen.offset_64));
    }
}
