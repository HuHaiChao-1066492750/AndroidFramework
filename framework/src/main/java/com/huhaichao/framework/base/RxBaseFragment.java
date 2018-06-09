package com.huhaichao.framework.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.framework.network.HttpRequestCallback;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/16.
 */

public abstract class RxBaseFragment extends RxFragment implements HttpRequestCallback<JSONObject> {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initData(view, savedInstanceState);
        return view;
    }

    protected abstract void initData(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();

    /**
     * 绑定生命周期
     */
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(FragmentEvent.STOP);
    }
}
