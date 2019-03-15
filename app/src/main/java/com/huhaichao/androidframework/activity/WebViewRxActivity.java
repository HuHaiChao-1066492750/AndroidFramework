package com.huhaichao.androidframework.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseRxActivity;
import com.huhaichao.framework.webview.X5WebView;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author HuHaiChao
 */
public class WebViewRxActivity extends BaseRxActivity {
    @BindView(R.id.frame_layout)
    protected FrameLayout frameLayout;

    private X5WebView mWebView;
    private Activity mActivity;

    @Override
    public void initData(Bundle bundle) {
        mActivity = this;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web_view_rx;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        BarUtils.setStatusBarVisibility(this, false);
        initWebView();
    }

    @Override
    public void doBusiness() {
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

    public void initWebView() {
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
            LogUtils.d(e.getMessage());
        }
        mWebView = new X5WebView(this, null);

        if (mWebView.getX5WebViewExtension() != null) {
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", true);// true表示标准全屏，false表示X5全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }

        frameLayout.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
                return super.onJsConfirm(webView, s, s1, jsResult);
            }

            View myVideoView;
            View myNormalView;
            CustomViewCallback callback;

            /**
             * 全屏播放配置
             */
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
                List<View> list = getAllChildViews(view);
                Log.d("hhc", "size== " + list.size());
                //12：缓存 13：小窗 14：分享 23：横竖屏
                //size等于26是先点播放，再点全屏
                //size等于29是先点全屏，再点播放
                if (list.size() == 26) {
                    list.get(12).setVisibility(View.INVISIBLE);
//                    list.get(13).setVisibility(View.INVISIBLE);
                    list.get(14).setVisibility(View.INVISIBLE);
                    list.get(23).setVisibility(View.INVISIBLE);
                } else if (list.size() == 29) {
                    list.get(15).setVisibility(View.INVISIBLE);
//                    list.get(16).setVisibility(View.INVISIBLE);
                    list.get(17).setVisibility(View.INVISIBLE);
                    list.get(26).setVisibility(View.INVISIBLE);
                }

                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                FrameLayout normalView = mWebView;
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;

            }

            @Override
            public void onHideCustomView() {
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }


            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
        mWebView.loadUrl("http://aaqqy.com/");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private List<View> getAllChildViews(View view) {
        List<View> list = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            Log.d("hhc", "ClassName: ------------start-------------" + vp.getAccessibilityClassName());
            for (int i = 0; i < vp.getChildCount(); i++) {
                View child = vp.getChildAt(i);
                Log.d("hhc", "ClassName: " + child.getAccessibilityClassName().toString());
                list.add(child);
                //再次 调用本身（递归）
                list.addAll(getAllChildViews(child));
            }
            Log.d("hhc", "ClassName: ---------------end----------" + vp.getAccessibilityClassName());
        }
        return list;
    }

}
