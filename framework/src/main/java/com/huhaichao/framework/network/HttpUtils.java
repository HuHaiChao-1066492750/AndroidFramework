package com.huhaichao.framework.network;

import com.alibaba.fastjson.JSONObject;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by HuHaiChao on 2018/6/1.
 */

public class HttpUtils {
    public static void get(LifecycleTransformer observable, String path, Map<String, String> params, HttpObserverListener observer) {
        ApiService service = RetrofitFactory.getInstance().create(ApiService.class);
        service.get(path, params)
                .compose(observable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void post(LifecycleTransformer observable, String path, JSONObject jsonObject, HttpObserverListener observer) {
        ApiService service = RetrofitFactory.getInstance().create(ApiService.class);
        service.post(path, jsonObject)
                .compose(observable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
