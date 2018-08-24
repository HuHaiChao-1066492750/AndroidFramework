package com.huhaichao.androidframework.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseActivity;

/**
 * Android的消息机制
 * ThreadLocal、Handle、MessageQueue、Looper
 */
public class MessageActivity extends BaseActivity {
    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {
        new Thread() {
            @Override
            public void run() {
                //Handler主要用于不同线程间的通信
                //MessageQueue:单链表，插入(enqueueMessage)和读取(next)，读取操作本身会伴随着删除操作
                Looper.prepare();//通过ThreadLocal<Looper>保存当前线程的Looper
                Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {//handler和looper关联起来了
                        return false;
                    }
                });//获取当前线程的Looper,dispatchMessage()处理消息
                Looper.loop();//取到当前Looper的MessageQueue，开始死循环取消息
                Message message = new Message();
                message.getCallback();
                handler.sendMessage(message);//handler和message关联起来了
            }
        };
    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {

    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {

    }
}
