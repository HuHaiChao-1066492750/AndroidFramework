package com.huhaichao.androidframework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.huhaichao.androidframework.base.BaseFragment;
import com.huhaichao.framework.network.HttpObserverListener;
import com.huhaichao.framework.network.HttpUtils;
import com.orhanobut.logger.Logger;

import butterknife.OnClick;

/**
 * Created by huhaichao on 2018/3/12.
 */

public class TaskFragment extends BaseFragment {

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    protected void initData(View view, Bundle savedInstanceState) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main1;
    }


    @OnClick(R.id.tv)
    protected void startOtherActivity() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("telephone", "13160675089");
        jsonObject.put("password", "BACCDC78404B329FE4A4B256FB5DE230");
        jsonObject.put("deviceId", "");
        jsonObject.put("deviceType", "20");
        //取消订阅时机 ：this.bindUntilEvent(FragmentEvent.STOP)
//        HttpUtils.post(bindToLife(), "http://192.168.1.126:8082/xkzp-app-webapp/authMember/login.json", jsonObject, new HttpObserverListener<JSONObject>(1002, this));
        HttpUtils.post(bindToLife(),"http://39.108.53.59:8080/xkzp-app-webapp/authMember/login.json", jsonObject, new HttpObserverListener<JSONObject>(1002, this));
//        HttpUtils.post("http://120.78.173.29:8082/smarthome/applianceMaster/queryApplianceList.json", jsonObject, new HttpObserverListener<JSONObject>(1001, this));
//        startActivity(new Intent(getActivity(), Main1Activity.class));
    }

    @OnClick(R.id.tv1)
    protected void startOtherActivity1() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberId", "d091d6a3-f6ea-495c-bc4a-e954d3fd89b0");
        //取消订阅时机 ：this.bindUntilEvent(FragmentEvent.STOP)
//        HttpUtils.post(bindToLife(), "http://192.168.1.122:8080/xkzp-app-webapp/authMember/login.json", jsonObject, new HttpObserverListener<JSONObject>(1002, this));
        HttpUtils.post(bindToLife(),"http://39.108.53.59:8080/xkzp-app-webapp/systemInfo/queryUserAddress.json", jsonObject, new HttpObserverListener<JSONObject>(1002, this));
//        HttpUtils.post("http://120.78.173.29:8082/smarthome/applianceMaster/queryApplianceList.json", jsonObject, new HttpObserverListener<JSONObject>(1001, this));
//        startActivity(new Intent(getActivity(), Main1Activity.class));
    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {

    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {
    }
}
