package com.huhaichao.androidframework.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ScreenUtils;
import com.huhaichao.framework.base.IRxIBaseActivity;
import com.huhaichao.framework.network.HttpRequestCallback;
import com.huhaichao.framework.widget.CustomDialog;

import butterknife.ButterKnife;


public abstract class BaseActivity extends IRxIBaseActivity implements HttpRequestCallback<JSONObject> {
    protected View mContentView;
    protected Activity mActivity;

    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        Bundle bundle = getIntent().getExtras();
        initData(bundle);//初始化数据
        //屏幕适配 start
        //在 setContentView();之前
        if (ScreenUtils.isPortrait()) {
            ScreenUtils.adaptScreen4VerticalSlide(this, 360);
        } else {
            ScreenUtils.adaptScreen4HorizontalSlide(this, 360);
        }
        //屏幕适配 end
        setBaseView(bindLayout());//绑定布局
        ButterKnife.bind(this);
        initView(savedInstanceState, mContentView);//初始化布局
        doBusiness();//业务操作
    }

    @SuppressLint("ResourceType")
    protected void setBaseView(@LayoutRes int layoutId) {
        if (layoutId <= 0) return;
        setContentView(mContentView = LayoutInflater.from(this).inflate(layoutId, null));
    }

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

    /**
     * 判断是否快速点击
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }
    //    public void exit() {
//        Intent intent = new Intent();
//        intent.setClass(mRxBaseActivity, LoginActivity.class);
//        intent.setAction(AgentConstants.Action.EXIT_APP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) {
            onWidgetClick(view);
        }
    }
}
