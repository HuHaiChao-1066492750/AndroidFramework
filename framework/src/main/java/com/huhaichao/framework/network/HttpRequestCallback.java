package com.huhaichao.framework.network;

/**
 * Created by HuHaiChao on 2018/6/1.
 */

public interface HttpRequestCallback<T> {
    void onFailureCallback(int requestCode, Throwable e);

    void onSuccessCallback(int requestCode, T t);
}
