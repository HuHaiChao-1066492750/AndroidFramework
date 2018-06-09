package com.huhaichao.androidframework;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.huhaichao.androidframework.base.BaseActivity;
import com.huhaichao.framework.network.HttpObserverListener;
import com.huhaichao.framework.network.HttpUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Main1Activity extends BaseActivity {
    private static final String TAG = "Main1Activity";
    @BindView(R.id.tv)
    protected TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main1);
    }

    @Override
    protected void initData() {

    }

    @TargetApi(Build.VERSION_CODES.N)
    @OnClick(R.id.tv)
    protected void startOtherActivity() {
//        Logger.t(TAG).d(Environment.getDataDirectory());//= /data
//        Logger.t(TAG).d(Environment.getDownloadCacheDirectory());// = /cache
//        Logger.t(TAG).d(Environment.getExternalStorageDirectory());// = /mnt/sdcard
//        Logger.t(TAG).d(Environment.getRootDirectory());// = /system
//        Logger.t(TAG).d(getPackageCodePath());// = /data/app/com.my.app-1.apk
//        Logger.t(TAG).d(getPackageResourcePath());// = /data/app/com.my.app-1.apk
//        Logger.t(TAG).d(getCacheDir());// = /data/data/com.my.app/cache
        Logger.t(TAG).d(getExternalCacheDir());// = /mnt/sdcard/Android/data/com.my.app/cache
        Logger.t(TAG).d(getExternalFilesDir(null));// = /mnt/sdcard/Android/data/com.my.app/files
//        Logger.t(TAG).d(getFilesDir());//= /data/data/com.my.app/files
        Logger.t(TAG).d(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());///storage/emulated/0/Android/data/packagename/files/Pictures



        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        Logger.t(TAG).d("拒绝后");
                        //可以提示为什么要这个权限
                        shouldRequest.again(true);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        Logger.t(TAG).d("已授权");
                        StringBuilder sb = new StringBuilder();
                        sb.append(getExternalFilesDir(null).getAbsolutePath());
                        sb.append(File.separator);
                        sb.append("hhc");
                        File file = new File(sb.toString());
                        Logger.t(TAG).d(sb.toString());
                        if (!file.exists()) {
                            if(!file.mkdirs()){
                                Logger.t(TAG).d("创建失败");
                            }else{
                                Logger.t(TAG).d("创建成功");
                            }
                        }else{
                            Logger.t(TAG).d("已存在");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            PermissionUtils.launchAppDetailsSettings();
                            Logger.t(TAG).d("打开设置");
                        }
                        Logger.t(TAG).d("已拒绝");
                    }
                })
                .request();
    }

    public String getFileCachePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCachePath());
        sb.append("file");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    public String getAppPath() {
        StringBuilder sb = new StringBuilder();
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            sb.append(Environment.getExternalStorageDirectory().getPath());
        } else {
            sb.append(Environment.getDataDirectory().getPath());
        }
        sb.append(File.separator);
        sb.append("android_framework");
        sb.append(File.separator);
        return sb.toString();
    }

    public String getCachePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAppPath());
        sb.append("cache");
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    public String getImageCachePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCachePath());
        sb.append("image");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    @OnClick(R.id.tv1)
    protected void tv1() {
        tv.setText(new SpanUtils()
                .appendLine("isConnected: " + NetworkUtils.isConnected())
                .appendLine("getMobileDataEnabled: " + NetworkUtils.getMobileDataEnabled())
                .appendLine("isMobileData: " + NetworkUtils.isMobileData())
                .appendLine("is4G: " + NetworkUtils.is4G())
                .appendLine("getWifiEnabled: " + NetworkUtils.getWifiEnabled())
                .appendLine("isWifiConnected: " + NetworkUtils.isWifiConnected())
                .appendLine("isWifiAvailable: " + NetworkUtils.isWifiAvailable())
                .appendLine("getNetworkOperatorName: " + NetworkUtils.getNetworkOperatorName())
                .appendLine("getNetworkTypeName: " + NetworkUtils.getNetworkType())
                .append("getIPAddress: " + NetworkUtils.getIPAddress(true))
                .create()
        );
    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {

    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {

    }
}
