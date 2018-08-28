package com.huhaichao.androidframework.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.framework.base.IRxIBaseFragment;
import com.huhaichao.framework.network.HttpRequestCallback;
import com.huhaichao.framework.widget.CustomDialog;

import butterknife.ButterKnife;

/**
 * Created by HuHaiChao on 2018/6/1.
 */

public abstract class BaseFragment extends IRxIBaseFragment implements HttpRequestCallback<JSONObject> {
    public static final String TAG = "BaseFragment";
    protected View mContentView;
    protected Activity mActivity;
    private long lastClick = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setBaseView(inflater, bindLayout());
        ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    protected void setBaseView(@NonNull LayoutInflater inflater, @LayoutRes int layoutId) {
        if (layoutId <= 0) return;
        mContentView = inflater.inflate(layoutId, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        initData(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        initView(savedInstanceState, mContentView);
        doBusiness();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        if (mContentView != null) {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }


    /**
     * 弹出消息
     */
    protected void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msgID) {
        Toast.makeText(mActivity, msgID, Toast.LENGTH_SHORT).show();
    }

    /**
     * 页面跳转
     */
    protected void startOtherActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, className);
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
        new CustomDialog.Builder(mActivity)
                .setTitle("智能家居")
                .setMessage(message)
                .setButton(which1, "确定", onClickListener)
                .setButton(which2, "取消", onClickListener)
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) {
            onWidgetClick(view);
        }
    }
}
