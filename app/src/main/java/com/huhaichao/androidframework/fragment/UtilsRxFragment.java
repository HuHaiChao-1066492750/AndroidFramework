package com.huhaichao.androidframework.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.activity.BrowserActivity;
import com.huhaichao.androidframework.activity.WebViewRxActivity;
import com.huhaichao.androidframework.base.BaseRxFragment;
import com.huhaichao.framework.network.retrofit.HttpObserverListener;
import com.huhaichao.framework.network.retrofit.HttpUtils;

import butterknife.BindView;

/**
 * Created by huhaichao on 2018/3/12.
 */

public class UtilsRxFragment extends BaseRxFragment {
    @BindView(R.id.fragment_utils_bt_1)
    protected Button fragment_utils_bt_1;
    @BindView(R.id.fragment_utils_bt_2)
    protected Button fragment_utils_bt_2;
    @BindView(R.id.fragment_utils_bt_web_view_1)
    protected Button fragment_utils_bt_web_view_1;
    @BindView(R.id.fragment_utils_bt_web_view_2)
    protected Button fragment_utils_bt_web_view_2;
    private String text = "服务商不管以任何形式ThisThisThis要求ThisThisThis is test!线下交易，都存在This is test!诈骗的风险，请提高警惕。This is test!欢迎相互关注。有不对的地方望指出和包容。谢谢！ ";

    public static UtilsRxFragment newInstance() {
        return new UtilsRxFragment();
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
        fragment_utils_bt_1.setOnClickListener(this);
        fragment_utils_bt_2.setOnClickListener(this);
        fragment_utils_bt_web_view_1.setOnClickListener(this);
        fragment_utils_bt_web_view_2.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_utils_bt_1:

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("memberId", "d091d6a3-f6ea-495c-bc4a-e954d3fd89b0");
                HttpUtils.post(bindToLife(), "http://39.108.78.23:8080/xkzp-app-webapp/bsCoupon/getUnReadCouponByMemberId.json", jsonObject, new HttpObserverListener<JSONObject>(1002, this));
//                new CustomDialog.Builder(getActivity())
////                        .setTitle("智能家居")
//                        .setMessage(StringUtils.toDBC(text))
//                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                EventBus.getDefault().post(new NetworkEvent());
//                            }
//                        })
//                        .setNegativeButton("取消", null)
//                        .setCanceledOnTouchOutside(false)
//                        .create()
//                        .show();
                break;
            case R.id.fragment_utils_bt_2:
//                startActivity(new Intent(getActivity(), BitmapRxActivity.class));
                doRequest();
            case R.id.fragment_utils_bt_web_view_1:
                startActivity(new Intent(getActivity(), BrowserActivity.class));
                break;
            case R.id.fragment_utils_bt_web_view_2:
                startActivity(new Intent(getActivity(), WebViewRxActivity.class));
                break;
            default:
                break;
        }
    }
}
