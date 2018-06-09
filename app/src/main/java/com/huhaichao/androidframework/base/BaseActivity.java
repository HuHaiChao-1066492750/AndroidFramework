package com.huhaichao.androidframework.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.huhaichao.framework.base.RxBaseActivity;
import com.huhaichao.framework.widget.CustomDialog;


public abstract class BaseActivity extends RxBaseActivity {
    /**
     * 页面跳转
     */
    protected void startOtherActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startOtherActivityForResult(Class<?> className, Bundle bundle,
                                               int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 弹出消息
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msgID) {
        Toast.makeText(this, msgID, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出对话框
     */
    protected void showInformationDialog(String message, int which1, int which2,
                                         DialogInterface.OnClickListener onClickListener) {
        new CustomDialog.Builder(this)
                .setTitle("智能家居")
                .setMessage(message)
                .setButton(which1, "确定", onClickListener)
                .setButton(which2, "取消", onClickListener)
                .setCancelable(false)
                .create()
                .show();
    }

    //    public void exit() {
//        Intent intent = new Intent();
//        intent.setClass(mRxBaseActivity, LoginActivity.class);
//        intent.setAction(AgentConstants.Action.EXIT_APP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//    }
}
