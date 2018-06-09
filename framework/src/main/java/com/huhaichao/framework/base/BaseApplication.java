package com.huhaichao.framework.base;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HuHaiChao on 2018/6/1.
 */

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static BaseApplication mBaseApplication;
    private static List<Activity> mActivityList = Collections.synchronizedList(new LinkedList<Activity>());
    private static WeakReference<Activity> mCurrentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
        //工具类
        Utils.init(this);
        initLogger();
        /**注册ActivityListener监听*/
        registerActivityListener();
    }

    public static BaseApplication getApplication() {
        return mBaseApplication;
    }

    //退出程序
    public static void exit() {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return;
        }
        for (Activity activity : mActivityList) {
            if (null != activity) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
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
     * 获取当前Activity（栈中最后一个压入得）
     */
    public static Activity getCurrentActivity() {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return null;
        }
        Activity activity = mActivityList.get(mActivityList.size() - 1);
        return activity;
    }

    /**
     * 获取当前Activity的弱引用
     */
    public static WeakReference<Activity> getActivityReference() {
        return mCurrentActivity;
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(0)         // （可选）要显示的方法行数。 默认2
//                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
//                .logStrategy(customLog) //（可选）更改要打印的日志策略。 默认LogCat
                .tag("hhc")   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    // TODO: 2018/6/8 程序意外终止、闪退之前
}
