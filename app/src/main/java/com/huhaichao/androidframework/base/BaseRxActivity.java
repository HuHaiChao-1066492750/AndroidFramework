package com.huhaichao.androidframework.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.huhaichao.androidframework.R;
import com.huhaichao.framework.event.NetworkEvent;
import com.huhaichao.framework.network.HttpRequestCallback;
import com.huhaichao.framework.receiver.NetworkChangeReceiver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


public abstract class BaseRxActivity extends com.huhaichao.framework.base.BaseRxActivity implements HttpRequestCallback<JSONObject> {
    public Activity mBaseActivity;
    private View mContentView;
    private boolean isRegistered = false;
    private static boolean isConnected = false;
    private NetworkChangeReceiver networkChangeReceiver;
    private long lastClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseActivity = this;
//        Slidr.attach(this);//右划返回，app会变卡顿
        Bundle bundle = getIntent().getExtras();
        //初始化数据
        initData(bundle);
        //屏幕适配 start
        //在 setContentView();之前
        if (ScreenUtils.isPortrait()) {
            ScreenUtils.adaptScreen4VerticalSlide(this, 360);
        } else {
            ScreenUtils.adaptScreen4HorizontalSlide(this, 360);
        }
        //屏幕适配 end
        //绑定布局
        setBaseView(bindLayout());
        ButterKnife.bind(this);
        //初始化布局
        initView(savedInstanceState, mContentView);
        initReceiver();//网络实时监听
        doBusiness();//业务操作
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @SuppressLint("ResourceType")
    protected void setBaseView(@LayoutRes int layoutId) {
        if (layoutId <= 0) {
            return;
        }
        setContentView(mContentView = LayoutInflater.from(this).inflate(layoutId, null));
    }

    /**
     * 判断是否快速点击
     */
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

    public void initReceiver() {
        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
        isRegistered = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 网络实时监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkEvent(NetworkEvent networkEvent) {
        if (NetworkUtils.isConnected()) {
            if (!isConnected) {
                isConnected = true;
                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                if (NetworkUtils.isMobileData()) {
                    ToastUtils.showShort(NetworkUtils.getNetworkOperatorName() + "流量连接");
                } else if (NetworkUtils.isWifiConnected()) {
                    ToastUtils.showShort("正在使用WIFI网络");
                } else {
                    ToastUtils.showShort("网络已连接");
                }
                LogUtils.dTag("hhc", "网络已连接");
            }
        } else {
            if (isConnected) {
                isConnected = false;
                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                ToastUtils.showShort("网络已断开");
                LogUtils.dTag("hhc", "网络已断开");
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (isRegistered) {
            unregisterReceiver(networkChangeReceiver);
        }
        super.onDestroy();
    }


    public void Toast(String msg) {
        resetToast();
    }

    private void resetToast() {
        ToastUtils.setMsgColor(0xFEFFFFFF);
        ToastUtils.setBgColor(0xFEFFFFFF);
        ToastUtils.setBgResource(-1);
        ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, getResources().getDimensionPixelSize(R.dimen.offset_64));
    }
}
