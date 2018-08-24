package com.huhaichao.androidframework.activity;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseActivity;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.BindView;
import butterknife.OnClick;

public class ThreadActivity extends BaseActivity {
    @BindView(R.id.activity_thread_bt_1)
    protected Button activity_thread_bt_1;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_thread;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {
        asyncTask();
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

    public void asyncTask() {
        AsyncTask asyncTask = new AsyncTask<String, Integer, Long>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //在主线程中执行，在异步任务执行之前
            }

            @Override
            protected Long doInBackground(String... params) {//数组型参数
                int count = params.length;
                //在线程池中执行
                publishProgress(0);
                return 0L;
            }

            @Override
            protected void onProgressUpdate(Integer... progress) {
                super.onProgressUpdate(progress);
                //在主线程中执行，当后台任务的执行进度发生改变时
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                //在主线程中执行，在异步任务执行完之后(接收doInBackground方法的参数)
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected void onCancelled(Long aLong) {
                super.onCancelled(aLong);
            }
        };
        asyncTask.cancel(true);
        asyncTask.executeOnExecutor(null);
        asyncTask.getStatus();
    }

    public void handlerThread() {
        IntentService intentService = new IntentService("Service") {
            @Override
            protected void onHandleIntent(Intent intent) {

            }
        };
    }

    public void service() {

    }

    public void threadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(4);
    }

    @OnClick(R.id.activity_thread_bt_1)
    protected void onButton() {

    }
}
