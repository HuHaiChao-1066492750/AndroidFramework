package com.huhaichao.androidframework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseRxActivity;

import butterknife.BindView;

/**
 * Android的消息机制
 * ThreadLocal、Handle、MessageQueue、Looper
 */
public class MessageRxActivity extends BaseRxActivity {
    @BindView(R.id.activity_message_bt_1)
    protected Button activity_message_bt_1;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        activity_message_bt_1.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {
//        new Thread() {
//            @Override
//            public void run() {
//                //Handler主要用于不同线程间的通信
//                //MessageQueue:单链表，插入(enqueueMessage)和读取(next)，读取操作本身会伴随着删除操作
//                Looper.prepare();//通过ThreadLocal<Looper>保存当前线程的Looper
//                Handler handler = new Handler(new Handler.Callback() {
//                    @Override
//                    public boolean handleMessage(Message msg) {//handler和looper关联起来了
//                        return false;
//                    }
//                });//获取当前线程的Looper,dispatchMessage()处理消息
//                Looper.loop();//取到当前Looper的MessageQueue，开始死循环取消息
//                Message message = new Message();
//                message.getCallback();
//                handler.sendMessage(message);//handler和message关联起来了
//            }
//        };
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.activity_message_bt_1:
                startActivity(new Intent(this, ThreadRxActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {

    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {

    }
}
