package com.huhaichao.framework.base;

import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.framework.network.HttpRequestCallback;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

public abstract class RxBaseActivity extends RxAppCompatActivity implements HttpRequestCallback<JSONObject> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 设置布局
     */
    protected abstract void setContentView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 绑定生命周期
     */
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.STOP);
    }
}
