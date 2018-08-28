package com.huhaichao.framework.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.Utils;
import com.huhaichao.framework.utils.CacheManager;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HuHaiChao on 2018/6/1.
 */

public class IBaseApplication extends Application {
    private static final String TAG = "IBaseApplication";
    private static IBaseApplication mIBaseApplication;
    private static List<Activity> mActivityList = Collections.synchronizedList(new LinkedList<Activity>());
    private static WeakReference<Activity> mCurrentActivity;
    private CrashUtils.OnCrashListener crashListener;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //检测当前进程名称是否为应用包名,为了只初始化一次应用配置
        if (!ProcessUtils.getCurrentProcessName().equals(getPackageName())) {
            return;
        }
        mIBaseApplication = this;
        // TODO: 2018/8/14 第三方SDK的初始化放线程里
        //工具类
        Utils.init(this);
        initLog();
        initCrash();
        /**注册ActivityListener监听*/
        registerActivityListener();
    }

    public static IBaseApplication getApplication() {
        return mIBaseApplication;
    }

    /**
     * 退出App
     */
    public static void exit() {
        AppUtils.exitApp();
    }

    /**
     * 重启App
     */
    public static void relaunch() {
        AppUtils.relaunchApp();
    }

    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    if (null == mActivityList) {
                        return;
                    }
                    mActivityList.add(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivityResumed(Activity activity) {
                    mCurrentActivity = new WeakReference<>(activity);
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == activity && mActivityList.isEmpty()) {
                        return;
                    }
                    if (mActivityList.contains(activity)) {
                        mActivityList.remove(activity);
                    }
                }
            });
        }
    }

    /**
     * 获取当前Activity（最后一个入栈的）
     */
    public static Activity getTopActivity() {
        return ActivityUtils.getTopActivity();
    }

    /**
     * 获取当前Activity的弱引用
     */
    public static WeakReference<Activity> getActivityReference() {
        return mCurrentActivity;
    }

    /**
     * 初始化日志工具
     */
    public void initLog() {
        LogUtils.getConfig()
//                .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
//                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(false)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir(CacheManager.getInstance().getLogFilesPath())// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("log")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
                .setStackOffset(0);// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
    }

    public void requiresPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        // TODO: 2018/8/28 添加选择对话框
                        shouldRequest.again(true);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        initCrash();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            // TODO: 2018/8/28 添加选择对话框
                            PermissionUtils.launchAppDetailsSettings();
                        }
                    }
                }).request();
    }

    /**
     * 程序意外终止、crash崩溃之前,日志保存和上传服务器
     */
    @SuppressLint("MissingPermission")
    private void initCrash() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requiresPermission();
            return;
        }
        CrashUtils.init(CacheManager.getInstance().getLogFilesPath(), new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                LogUtils.e(crashInfo);
                // TODO: 2018/8/27 用户提示信息
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(getApplication(), "程序出现异常，即将重启", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }.start();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                }
                AppUtils.relaunchApp();
                // TODO: 2018/8/28 跳转到自定义崩溃页
//                Intent intent = new Intent();
//                intent.setClass(getApplication(), BitmapActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
            }
        });
    }
}
