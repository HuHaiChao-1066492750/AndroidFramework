package com.huhaichao.androidframework.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseRxFragment;
import com.huhaichao.framework.event.UpdateEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by huhaichao on 2018/3/12.
 */

public class WidgetsRxFragment extends BaseRxFragment {
    @BindView(R.id.fragment_widgets_bt_toast)
    protected Button fragment_widgets_bt_toast;

    public static WidgetsRxFragment newInstance() {
        return new WidgetsRxFragment();
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_widgets;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        fragment_widgets_bt_toast.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_widgets_bt_toast:
                EventBus.getDefault().post(new UpdateEvent());
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
