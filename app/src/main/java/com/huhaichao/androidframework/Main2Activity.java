package com.huhaichao.androidframework;

import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.androidframework.base.BaseActivity;
import com.huhaichao.framework.network.HttpUtils;
import com.huhaichao.framework.network.HttpObserverListener;

import butterknife.OnClick;

public class Main2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main2);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv)
    protected void startOtherActivity() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("telephone", "13160675089");
        jsonObject.put("password", "123456");
        jsonObject.put("deviceId", "");
        jsonObject.put("deviceType", "20");
        HttpUtils.post(bindToLife(),"http://39.108.53.59:8080/xkzp-app-webapp/authMember/login.json", jsonObject, new HttpObserverListener<JSONObject>(1002, this));
//        HttpUtils.post("http://120.78.173.29:8082/smarthome/applianceMaster/queryApplianceList.json", jsonObject, new HttpObserverListener<JSONObject>(1001, this));
//        startActivity(new Intent(this, Main1Activity.class));
    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {

    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {

    }
}
