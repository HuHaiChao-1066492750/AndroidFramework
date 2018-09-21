package com.huhaichao.framework.network.retrofit;

/**
 * @author HuHaiChao
 */

public interface HttpRequestCallback<T> {
    /**
     * 失败回调
     *
     * @param requestCode
     * @param e
     */
    void onFailureCallback(int requestCode, Throwable e);

    /**
     * 成功回调
     *
     * @param requestCode
     * @param t
     */
    void onSuccessCallback(int requestCode, T t);
}
