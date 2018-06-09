package com.huhaichao.androidframework.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.huhaichao.framework.base.RxBaseActivity;
import com.huhaichao.framework.base.RxBaseFragment;
import com.huhaichao.framework.widget.CustomDialog;

/**
 * Created by HuHaiChao on 2018/6/1.
 */

public abstract class BaseFragment extends RxBaseFragment {
    protected RxBaseActivity mRxBaseActivity;

    //获取宿主Activity
    protected RxBaseActivity getBaseActivity() {
        return mRxBaseActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mRxBaseActivity = (RxBaseActivity) activity;
    }

    /**
     * 弹出消息
     */
    protected void showToast(String msg) {
        Toast.makeText(mRxBaseActivity, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msgID) {
        Toast.makeText(mRxBaseActivity, msgID, Toast.LENGTH_SHORT).show();
    }

    /**
     * 页面跳转
     */
    protected void startOtherActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mRxBaseActivity, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    //    public void exit() {
//        Intent intent = new Intent();
//        intent.setClass(mRxBaseActivity, LoginActivity.class);
//        intent.setAction(AgentConstants.Action.EXIT_APP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//    }

    /**
     * 弹出对话框
     */
    protected void showInformationDialog(String message, int which1, int which2,
                                         DialogInterface.OnClickListener onClickListener) {
        new CustomDialog.Builder(mRxBaseActivity)
                .setTitle("智能家居")
                .setMessage(message)
                .setButton(which1, "确定", onClickListener)
                .setButton(which2, "取消", onClickListener)
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    protected void initData(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
