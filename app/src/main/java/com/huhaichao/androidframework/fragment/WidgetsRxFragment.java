package com.huhaichao.androidframework.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseRxFragment;
import com.huhaichao.framework.event.UpdateEvent;
import com.huhaichao.framework.network.retrofit.HttpObserverListener;
import com.huhaichao.framework.network.retrofit.HttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

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
//                EventBus.getDefault().post(new UpdateEvent());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("telephone", "13160675089");
                jsonObject.put("password", "BACCDC78404B329FE4A4B256FB5DE230");
                jsonObject.put("deviceId", "");
                jsonObject.put("deviceType", "20");
                //取消订阅时机 ：this.bindUntilEvent(FragmentEvent.STOP)
                Map map=new HashMap();
                HttpUtils.get(bindToLife(), "http://192.168.0.111:8080/index.jsp", map, new HttpObserverListener<JSONObject>(1001, this));
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {
        LogUtils.dTag("hhc",e.getMessage());
    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {
        switch (requestCode) {
            case 1001:
                LogUtils.dTag("hhc",jsonObject.toString());
                break;
            default:
                break;
        }
    }
}
