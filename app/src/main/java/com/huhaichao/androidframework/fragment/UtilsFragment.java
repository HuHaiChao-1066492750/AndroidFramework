package com.huhaichao.androidframework.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseFragment;
import com.huhaichao.framework.network.HttpObserverListener;
import com.huhaichao.framework.network.HttpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huhaichao on 2018/3/12.
 */

public class UtilsFragment extends BaseFragment {
    @BindView(R.id.activity_main1_bt_2)
    protected Button activity_main1_bt_2;

    public static UtilsFragment newInstance() {
        return new UtilsFragment();
    }

    @OnClick(R.id.activity_main1_bt_1)
    protected void startOtherActivity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("系统异常")
                .setMessage("当前应用出现崩溃，是否重启？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AppUtils.relaunchApp();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AppUtils.exitApp();
                    }
                }).show();
    }

    public void doRequest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("telephone", "13160675089");
        jsonObject.put("password", "BACCDC78404B329FE4A4B256FB5DE230");
        jsonObject.put("deviceId", "");
        jsonObject.put("deviceType", "20");
        //取消订阅时机 ：this.bindUntilEvent(FragmentEvent.STOP)
        HttpUtils.post(bindToLife(), "http://39.108.78.23:8080/xkzp-app-webapp/authMember/login.json", jsonObject, new HttpObserverListener<JSONObject>(1002, this));
//        HttpUtils.post(bindToLife(),"http://39.108.53.59:8080/xkzp-app-webapp/authMember/login.json", jsonObject, new HttpObserverListener<JSONObject>(1002, this));
//        HttpUtils.post("http://120.78.173.29:8082/smarthome/applianceMaster/queryApplianceList.json", jsonObject, new HttpObserverListener<JSONObject>(1001, this));

    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {

    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {
    }


    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_utils;
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

    @OnClick(R.id.activity_main1_bt_2)
    protected void onButton2() {
        throw new NullPointerException("crash test");
    }
}
