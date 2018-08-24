package com.huhaichao.framework.network;


import android.content.Context;
import android.content.DialogInterface;

import com.huhaichao.framework.R;
import com.huhaichao.framework.base.BaseApplication;
import com.huhaichao.framework.widget.CustomNotifyDialog;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by HuHaiChao on 2018/6/1.
 * Rxjava2.0开始:Observer
 */

public class HttpObserverListener<T> implements Observer<T>, DialogInterface.OnDismissListener {
    private static final String TAG = "HttpObserverListener";
    private Context mContext;
    private HttpRequestCallback httpRequestCallback;
    private Disposable disposable = null;//注意内存泄漏
    private int requestCode;
    private CustomNotifyDialog customNotifyDialog = null;//横竖屏切换有问题,context不存在了，回调时对customNotifyDialog的操作有问题
                                                         //加入生命周期解决问题

    public HttpObserverListener(int requestCode, HttpRequestCallback httpRequestCallback) {
        this.mContext = BaseApplication.getActivityReference().get();//注意内存泄漏
        this.requestCode = requestCode;
        this.httpRequestCallback = httpRequestCallback;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
        if (customNotifyDialog == null) {
            customNotifyDialog = new CustomNotifyDialog(mContext);
            customNotifyDialog.setIconType(CustomNotifyDialog.ICON_TYPE_LOADING);
            customNotifyDialog.setMessage(mContext.getString(R.string.common_loading));
            customNotifyDialog.setCanceledOnTouchOutside(false);
            customNotifyDialog.setCancelable(true);
//            customNotifyDialog.setOnCancelListener();
            customNotifyDialog.setOnDismissListener(this);
        }

        if (!customNotifyDialog.isShowing()) {
            customNotifyDialog.show();
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        httpRequestCallback.onSuccessCallback(requestCode, t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        // TODO: 2018/6/8  根据网络错误给予对应的提示
        if (customNotifyDialog != null && customNotifyDialog.isShowing()) {
//            Activity activity = customNotifyDialog.getOwnerActivity();
//            if (activity != null && !activity.isFinishing()) {
            customNotifyDialog.dismiss();
//            }
        }
        customNotifyDialog = null;
        httpRequestCallback.onFailureCallback(requestCode, e);
    }

    @Override
    public void onComplete() {
        if (customNotifyDialog != null && customNotifyDialog.isShowing()) {
//            Activity activity = customNotifyDialog.getOwnerActivity();
//            if (activity != null && !activity.isFinishing()) {
            customNotifyDialog.dismiss();
//            }
        }
        customNotifyDialog = null;
    }

    //如果处于订阅状态，则取消订阅
    @Override
    public void onDismiss(DialogInterface dialog) {
        if (disposable != null) {
//            Logger.t(TAG).d("处于订阅状态");
            disposable.dispose();
        }
        disposable = null;
    }
}
